package com.hrbatovic.micronaut.master.cart.api;

import com.hrbatovic.micronaut.master.cart.JwtUtil;
import com.hrbatovic.micronaut.master.cart.db.entities.CartEntity;
import com.hrbatovic.micronaut.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.micronaut.master.cart.db.entities.ProductEntity;
import com.hrbatovic.micronaut.master.cart.db.repositories.CartRepository;
import com.hrbatovic.micronaut.master.cart.db.repositories.ProductRepository;
import com.hrbatovic.micronaut.master.cart.mapper.MapUtil;
import com.hrbatovic.micronaut.master.cart.messaging.model.out.CheckoutStartedEvent;
import com.hrbatovic.micronaut.master.cart.messaging.producers.CheckoutStartedEventProducer;
import com.hrbatovic.micronaut.master.cart.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class CartApiService implements ShoppingCartApi{

    @Inject
    JwtUtil jwtUtil;

    @Inject
    CartRepository cartRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    CheckoutStartedEventProducer checkoutStartedEventProducer;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse addProductToCart(AddCartProductRequest addCartProductRequest) {
        UUID userId = UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub"));

        ProductEntity productEntity = findProductById(addCartProductRequest.getProductId());

        if(productEntity.isDeleted()){
            throw new RuntimeException("This product is deleted and not active anymore.");
        }

        if(!productEntity.isLicenseAvailable()){
            throw new RuntimeException("This product currently has no licenses.");
        }


        CartEntity cartEntity = findCartByIdOrEmpty(userId);

        CartProductEntity updatedCartItem = addOrUpdateCartProduct(
                cartEntity, productEntity, addCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);

        cartRepository.update(cartEntity);

        return new CartProductResponse(updatedCartItem.getQuantity() > addCartProductRequest.getQuantity() ? "Product quantity updated in cart." : "Product added to cart.", mapper.map(updatedCartItem));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CheckoutResponse checkoutCart(StartCheckoutRequest startCheckoutRequest) {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));

        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new RuntimeException("The cart is empty");
        }

        List<UUID> productIds = cartEntity.getCartProducts()
                .stream()
                .map(CartProductEntity::getProductId)
                .toList();

        List<ProductEntity> products = productRepository.findByProductIds(productIds);

        if (products.size() < productIds.size()) {
            throw new RuntimeException("Some products in the cart are missing or invalid.");
        }

        products.forEach(product -> {
            if (product.isDeleted()) {
                throw new RuntimeException("Product " + product.getTitle() + " is deleted.");
            }
            if(!product.isLicenseAvailable()){
                throw new RuntimeException("Product " + product.getTitle() + " currently has no licenses.");
            }
        });

        CheckoutStartedEvent checkoutStartedEvent = buildCheckoutStartedEvent(startCheckoutRequest, cartEntity);
        checkoutStartedEventProducer.send(checkoutStartedEvent);


        return new CheckoutResponse("Checkout started successfully.", cartEntity.getId());
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public DeleteCartProductResponse deleteCartProduct(UUID productId) {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));

        removeCartProduct(cartEntity, productId);
        recalculateCartTotals(cartEntity);

        if(cartEntity.getTotalItems().equals(0)){
            cartRepository.delete(cartEntity);
        }else{
            cartRepository.update(cartEntity);
        }

        return new DeleteCartProductResponse("Cart item successfully deleted.");
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public EmptyCartResponse emptyCart() {
        CartEntity cartEntity = findCartByUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));
        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new RuntimeException("The cart is already empty");
        }
        cartRepository.delete(cartEntity);
        return new EmptyCartResponse("Cart has been successfully emptied.");
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartResponse getCartProducts() {
        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));

        return mapper.map(cartEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse updateCartProduct(UUID productId, UpdateCartProductRequest updateCartProductRequest) {

        CartEntity cartEntity = findCartByIdOrEmpty(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));

        CartProductEntity cartProduct = updateCartProductQuantity(cartEntity, productId, updateCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);
        cartRepository.update(cartEntity);

        return new CartProductResponse("Cart product updated successfully.", mapper.map(cartProduct));
    }

    public ProductEntity findProductById(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);

        if(productEntity == null){
            throw new RuntimeException("Product not found.");
        }

        return productEntity;
    }

    public CartEntity findCartByIdOrEmpty(UUID userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElse(null);
        if(cartEntity != null){
            return cartEntity;
        }

        cartEntity = new CartEntity();
        cartEntity.setUserId(userId);
        cartEntity.setCartProducts(new ArrayList<>());
        cartEntity.setTotalItems(0);
        cartEntity.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cartEntity);
        return cartEntity;

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

    public void recalculateCartTotals(CartEntity cartEntity) {
        cartEntity.setTotalItems(cartEntity.getCartProducts().stream().mapToInt(CartProductEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartProducts().stream()
                .map(CartProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void removeCartProduct(CartEntity cartEntity, UUID productId) {
        boolean removed = cartEntity.getCartProducts().removeIf(item -> item.getProductId().equals(productId));
        if (!removed) {
            throw new RuntimeException("Product not found in cart.");
        }
    }

    public CartEntity findCartByUserId(UUID userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElse(null);
        if(cartEntity == null){
            throw new RuntimeException("Cart not found.");
        }

        return cartEntity;
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
                .orElseThrow(() -> new RuntimeException("Cart product not found."));
    }

    private CheckoutStartedEvent buildCheckoutStartedEvent(StartCheckoutRequest startCheckoutRequest, CartEntity cartEntity) {
        return new CheckoutStartedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))
                .setUserEmail(jwtUtil.getClaimFromSecurityContext("email"))
                .setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")))
                .setPaymentMethod(startCheckoutRequest.getPaymentMethod())
                .setPaymentToken(startCheckoutRequest.getPaymentToken())
                .setRequestCorrelationId(UUID.randomUUID())
                .setCart(mapper.toCartPayload(cartEntity));
    }
}
