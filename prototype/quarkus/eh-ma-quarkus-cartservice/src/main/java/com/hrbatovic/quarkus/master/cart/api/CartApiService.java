package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.CartProductsApi;
import com.hrbatovic.master.quarkus.cart.model.*;
import com.hrbatovic.quarkus.master.cart.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.cart.mapper.MapUtil;
import com.hrbatovic.quarkus.master.cart.messaging.model.out.CheckoutStartedEvent;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class CartApiService implements CartProductsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    @Claim(standard = Claims.email)
    String emailClaim;

    @Inject
    @Claim("sid")
    String sessionIdClaim;

    @Inject
    @Channel("checkout-started-out")
    Emitter<CheckoutStartedEvent> checkoutStartedEmitter;

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response addProductToCart(AddCartProductRequest addCartProductRequest) {
        ApiInputValidator.validateAddProductToCart(addCartProductRequest);
        ApiInputValidator.validateQuantity(addCartProductRequest.getQuantity());

        UUID userId = UUID.fromString(userSubClaim);
        ProductEntity productEntity = findProductById(addCartProductRequest.getProductId());

        if(productEntity.isDeleted()){
            throw new EhMaException(400, "This product is deleted and not active anymore.");
        }

        if(!productEntity.isLicenseAvailable()){
            throw new EhMaException(400, "This product currently has no licenses.");
        }

        CartEntity cartEntity = findCartByIdOrEmpty(userId);

        CartProductEntity updatedCartItem = addOrUpdateCartProduct(
                cartEntity, productEntity, addCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return Response.ok(new CartProductResponse()
                .message(updatedCartItem.getQuantity() > addCartProductRequest.getQuantity()
                        ? "Product quantity updated in cart."
                        : "Product added to cart.")
                .product(mapper.map(updatedCartItem))).status(200).build();
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response checkoutCart(StartCheckoutRequest startCheckoutRequest) {
        ApiInputValidator.validateCheckoutCart(startCheckoutRequest);
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));

        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new EhMaException(400, "The cart is empty");
        }

        List<UUID> productIds = cartEntity.getCartProducts()
                .stream()
                .map(CartProductEntity::getProductId)
                .collect(Collectors.toList());

        List<ProductEntity> products = ProductEntity.list("_id in ?1", productIds);

        if (products.size() < productIds.size()) {
            throw new EhMaException(400, "Some products in the cart are missing or invalid.");
        }

        products.forEach(product -> {
            if (product.isDeleted()) {
                throw new EhMaException(400, "Product " + product.getTitle() + " is deleted.");
            }
            if(!product.isLicenseAvailable()){
                throw new EhMaException(400, "Product " + product.getTitle() + " currently has no licenses.");
            }
        });

        CheckoutStartedEvent checkoutStartedEvent = buildCheckoutStartedEvent(startCheckoutRequest, cartEntity);
        checkoutStartedEmitter.send(checkoutStartedEvent);

        return Response.ok(new CheckoutResponse()
                .message("Checkout started successfully.")
                .orderId(cartEntity.getId())).status(200).build();
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response deleteCartProduct(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));
        removeCartProduct(cartEntity, productId);

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return Response.ok(new DeleteCartProductResponse().message("Cart item successfully deleted.")).status(200).build();
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response emptyCart() {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));
        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new EhMaException(400, "The cart is already empty");
        }
        cartEntity.delete();

        return Response.ok(new EmptyCartResponse().message("Cart has been successfully emptied.")).status(200).build();
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response getCartProducts() {
        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(userSubClaim));
        return Response.ok(mapper.map(cartEntity)).status(200).build();
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public Response updateCartProduct(UUID productId, UpdateCartProductRequest updateCartProductRequest) {
        ApiInputValidator.validateProductId(productId);
        ApiInputValidator.validateUpdateCartProduct(updateCartProductRequest);
        ApiInputValidator.validateQuantity(updateCartProductRequest.getQuantity());


        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(userSubClaim));
        CartProductEntity cartProduct = updateCartProductQuantity(cartEntity, productId, updateCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return Response.ok(new CartProductResponse()
                .message("Cart product updated successfully.")
                .product(mapper.map(cartProduct))).status(200).build();
    }

    public CartEntity findCartByUserId(UUID userId) {
        CartEntity cartEntity = CartEntity.findByUserId(userId);
        if(cartEntity == null){
            throw new EhMaException(404, "Cart not found.");
        }

        return cartEntity;
    }

    public ProductEntity findProductById(UUID productId) {
        ProductEntity productEntity = ProductEntity.findById(productId);

        if(productEntity == null){
            throw new EhMaException(404, "Product not found.");
        }

        return productEntity;
    }

    public CartProductEntity addOrUpdateCartProduct(CartEntity cartEntity, ProductEntity productEntity, int quantity) {
        return cartEntity.getCartProducts().stream()
                .filter(item -> item.getProductId().equals(productEntity.getId()))
                .findFirst()
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    existingItem.setTotalPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())));
                    return existingItem;
                })
                .orElseGet(() -> {
                    CartProductEntity newItem = mapper.map(productEntity, quantity, productEntity.getPrice().multiply(BigDecimal.valueOf(quantity)));
                    cartEntity.getCartProducts().add(newItem);
                    return newItem;
                });
    }

    public void removeCartProduct(CartEntity cartEntity, UUID productId) {
        boolean removed = cartEntity.getCartProducts().removeIf(item -> item.getProductId().equals(productId));
        if (!removed) {
            throw new EhMaException(404, "Product not found in cart.");
        }
    }

    public CartProductEntity updateCartProductQuantity(CartEntity cartEntity, UUID productId, int quantity) {
        return cartEntity.getCartProducts().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .map(cartProduct -> {
                    cartProduct.setQuantity(quantity);
                    cartProduct.setTotalPrice(cartProduct.getProductPrice().multiply(BigDecimal.valueOf(quantity)));
                    return cartProduct;
                })
                .orElseThrow(() -> new EhMaException(404, "Cart product not found."));
    }

    public void recalculateCartTotals(CartEntity cartEntity) {
        cartEntity.setTotalItems(cartEntity.getCartProducts().stream().mapToInt(CartProductEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartProducts().stream()
                .map(CartProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public CartEntity findCartByIdOrEmpty(UUID userId) {
        CartEntity cartEntity = CartEntity.findByUserId(userId);
        if(cartEntity != null){
            return  cartEntity;
        }

        cartEntity = new CartEntity();
        cartEntity.setUserId(userId);
        cartEntity.setCartProducts(new ArrayList<>());
        cartEntity.setTotalItems(0);
        cartEntity.setTotalPrice(BigDecimal.ZERO);
        return cartEntity;

    }

    private CheckoutStartedEvent buildCheckoutStartedEvent(StartCheckoutRequest startCheckoutRequest, CartEntity cartEntity) {
        return new CheckoutStartedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(UUID.fromString(userSubClaim))
                .setUserEmail(emailClaim)
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setPaymentMethod(startCheckoutRequest.getPaymentMethod())
                .setPaymentToken(startCheckoutRequest.getPaymentToken())
                .setRequestCorrelationId(UUID.randomUUID())
                .setCart(mapper.toCartPayload(cartEntity));
    }

}
