package com.hrbatovic.springboot.master.cart.api;

import com.hrbatovic.master.springboot.cart.api.CartProductsApi;
import com.hrbatovic.master.springboot.cart.model.*;
import com.hrbatovic.springboot.master.cart.ApiInputValidator;
import com.hrbatovic.springboot.master.cart.ClaimUtils;
import com.hrbatovic.springboot.master.cart.db.entities.CartEntity;
import com.hrbatovic.springboot.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.springboot.master.cart.db.entities.ProductEntity;
import com.hrbatovic.springboot.master.cart.db.repositories.CartRepository;
import com.hrbatovic.springboot.master.cart.db.repositories.ProductRepository;
import com.hrbatovic.springboot.master.cart.exceptions.EhMaException;
import com.hrbatovic.springboot.master.cart.mapper.MapUtil;
import com.hrbatovic.springboot.master.cart.messaging.model.out.CheckoutStartedEvent;
import com.hrbatovic.springboot.master.cart.messaging.producers.CheckoutStartedEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CartApiService implements CartProductsApi {

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CheckoutStartedEventProducer checkoutStartedEventProducer;

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public CartProductResponse addProductToCart(AddCartProductRequest addCartProductRequest) {
        ApiInputValidator.validateAddProductToCart(addCartProductRequest);
        ApiInputValidator.validateQuantity(addCartProductRequest.getQuantity());
        UUID userId = claimUtils.getUUIDClaim("sub");

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

        cartRepository.save(cartEntity);

        return new CartProductResponse(updatedCartItem.getQuantity() > addCartProductRequest.getQuantity() ? "Product quantity updated in cart." : "Product added to cart.", mapper.map(updatedCartItem));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public CheckoutResponse checkoutCart(StartCheckoutRequest startCheckoutRequest) {
        ApiInputValidator.validateCheckoutCart(startCheckoutRequest);
        CartEntity cartEntity = findCartByUserId(claimUtils.getUUIDClaim("sub"));

        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new EhMaException(400, "The cart is empty");
        }

        List<UUID> productIds = cartEntity.getCartProducts()
                .stream()
                .map(CartProductEntity::getProductId)
                .toList();

        List<ProductEntity> products = productRepository.findByProductIds(productIds);

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
        checkoutStartedEventProducer.send(checkoutStartedEvent);


        return new CheckoutResponse("Checkout started successfully.", cartEntity.getId());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public DeleteCartProductResponse deleteCartProduct(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        CartEntity cartEntity = findCartByUserId(claimUtils.getUUIDClaim("sub"));

        removeCartProduct(cartEntity, productId);
        recalculateCartTotals(cartEntity);

        if(cartEntity.getTotalItems().equals(0)){
            cartRepository.delete(cartEntity);
        }else{
            cartRepository.save(cartEntity);
        }

        return new DeleteCartProductResponse("Cart item successfully deleted.");
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public EmptyCartResponse emptyCart() {
        CartEntity cartEntity = findCartByUserId(claimUtils.getUUIDClaim("sub"));
        if(cartEntity == null || cartEntity.getCartProducts() == null || cartEntity.getCartProducts().isEmpty()){
            throw new EhMaException(400, "The cart is already empty");
        }
        cartRepository.delete(cartEntity);
        return new EmptyCartResponse("Cart has been successfully emptied.");
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public CartResponse getCartProducts() {
        CartEntity cartEntity = findCartByIdOrEmpty(claimUtils.getUUIDClaim("sub"));

        return mapper.map(cartEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public CartProductResponse updateCartProduct(UUID productId, UpdateCartProductRequest updateCartProductRequest) {
        ApiInputValidator.validateProductId(productId);
        ApiInputValidator.validateUpdateCartProduct(updateCartProductRequest);
        ApiInputValidator.validateQuantity(updateCartProductRequest.getQuantity());

        CartEntity cartEntity = findCartByIdOrEmpty(claimUtils.getUUIDClaim("sub"));

        CartProductEntity cartProduct = updateCartProductQuantity(cartEntity, productId, updateCartProductRequest.getQuantity());

        recalculateCartTotals(cartEntity);
        cartRepository.save(cartEntity);

        return new CartProductResponse("Cart product updated successfully.", mapper.map(cartProduct));

    }

    public ProductEntity findProductById(UUID productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);

        if(productEntity == null){
            throw new EhMaException(404, "Product not found.");
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

    public CartEntity findCartByUserId(UUID userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElse(null);
        if(cartEntity == null){
            throw new EhMaException(404, "Cart not found.");
        }

        return cartEntity;
    }

    private CheckoutStartedEvent buildCheckoutStartedEvent(StartCheckoutRequest startCheckoutRequest, CartEntity cartEntity) {
        return new CheckoutStartedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setPaymentMethod(startCheckoutRequest.getPaymentMethod())
                .setPaymentToken(startCheckoutRequest.getPaymentToken())
                .setRequestCorrelationId(UUID.randomUUID())
                .setCart(mapper.toCartPayload(cartEntity));
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



}
