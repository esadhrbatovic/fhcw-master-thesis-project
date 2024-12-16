package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.CartProductsApi;
import com.hrbatovic.master.quarkus.cart.model.*;
import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.mapper.MapUtil;
import com.hrbatovic.quarkus.master.cart.messaging.model.out.CheckoutStartedEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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
    public CartProductResponse addProductToCart(AddCartProductRequest addCartProductRequest) {
        UUID userId = UUID.fromString(userSubClaim);
        ProductEntity productEntity = findProductById(addCartProductRequest.getProductId());

        CartEntity cartEntity = findCartByIdOrEmpty(userId);

        CartProductEntity updatedCartItem = addOrUpdateCartProduct(
                cartEntity, productEntity, addCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return new CartProductResponse()
                .message(updatedCartItem.getQuantity() > addCartProductRequest.getQuantity()
                        ? "Product quantity updated in cart."
                        : "Product added to cart.")
                .product(mapper.map(updatedCartItem));
    }

    @Override
    public CheckoutResponse checkoutCart(StartCheckoutRequest startCheckoutRequest) {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));

        CheckoutStartedEvent checkoutStartedEvent = buildCheckoutStartedEvent(startCheckoutRequest, cartEntity);
        checkoutStartedEmitter.send(checkoutStartedEvent);

        return new CheckoutResponse()
                .message("Checkout started successfully.")
                .orderId(cartEntity.getId());
    }

    @Override
    public DeleteCartProductResponse deleteCartProduct(UUID productId) {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));
        removeCartProduct(cartEntity, productId);

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return new DeleteCartProductResponse().message("Cart item successfully deleted.");
    }

    @Override
    public EmptyCartResponse emptyCart() {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(userSubClaim));
        cartEntity.delete();

        return new EmptyCartResponse().message("Cart has been successfully emptied.");
    }

    @Override
    public CartResponse getCartProducts() {
        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(userSubClaim));
        return mapper.map(cartEntity);
    }

    @Override
    public CartProductResponse updateCartProduct(UUID productId, UpdateCartProductRequest request) {
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            throw new WebApplicationException("Invalid quantity. Quantity must be at least 1.", Response.Status.BAD_REQUEST);
        }

        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(userSubClaim));
        CartProductEntity cartProduct = updateCartProductQuantity(cartEntity, productId, request.getQuantity());

        recalculateCartTotals(cartEntity);
        cartEntity.persistOrUpdate();

        return new CartProductResponse()
                .message("Cart product updated successfully.")
                .product(mapper.map(cartProduct));
    }

    public CartEntity findCartByUserId(UUID userId) {
        CartEntity cartEntity = CartEntity.findByUserId(userId);
        if(cartEntity == null){
            throw new WebApplicationException("Cart not found.", Response.Status.NOT_FOUND);
        }

        return cartEntity;
    }

    public ProductEntity findProductById(UUID productId) {
        ProductEntity productEntity = ProductEntity.findById(productId);

        if(productEntity == null){
            throw new WebApplicationException("Product not found.", Response.Status.NOT_FOUND);
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
            throw new WebApplicationException("Product not found in cart.", Response.Status.NOT_FOUND);
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
                .orElseThrow(() -> new WebApplicationException("Cart product not found.", Response.Status.NOT_FOUND));
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
                .setCart(mapper.toCartPayload(cartEntity));
    }

}
