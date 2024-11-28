package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.CartProductsApi;
import com.hrbatovic.master.quarkus.cart.model.*;
import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.messaging.model.CheckoutStartedEventPayload;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import com.hrbatovic.quarkus.master.cart.mapper.Mapper;
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
    @Claim(standard = Claims.sub)
    String userSub;

    @Inject
    @Channel("checkout-started-out")
    Emitter<CheckoutStartedEventPayload> checkoutStartedEmmiter;


    @Override
    public CartProductResponse addProductToCart(AddCartProductRequest addCartProductRequest) {
        CartEntity cartEntity = CartEntity.findByUserId(UUID.fromString(userSub));
        CartProductResponse cartItemResponse = new CartProductResponse();

        ProductEntity productEntity = ProductEntity.findById(addCartProductRequest.getProductId());
        if (productEntity == null) {
            throw new WebApplicationException("Product not found.", Response.Status.NOT_FOUND);
        }

        if (cartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setUserId(UUID.fromString(userSub));
            cartEntity.setCartProducts(new ArrayList<>());
            cartEntity.setTotalItems(0);
            cartEntity.setTotalPrice(BigDecimal.ZERO);
        }

        CartProductEntity existingCartItem = cartEntity.getCartProducts().stream()
                .filter(item -> item.getProductId().equals(productEntity.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + addCartProductRequest.getQuantity());
            existingCartItem.setTotalPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));

            cartItemResponse.setMessage("Product quantity updated in cart.");
            cartItemResponse.setProduct(Mapper.map(existingCartItem));
        } else {
            CartProductEntity newCartItem = new CartProductEntity();
            newCartItem.setProductId(productEntity.getId());
            newCartItem.setQuantity(addCartProductRequest.getQuantity());
            newCartItem.setTotalPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(addCartProductRequest.getQuantity())));
            newCartItem.setProductTitle(productEntity.getTitle());
            newCartItem.setProductPrice(productEntity.getPrice());

            cartEntity.getCartProducts().add(newCartItem);

            cartItemResponse.setMessage("Product added to cart.");
            cartItemResponse.setProduct(Mapper.map(newCartItem));
        }

        cartEntity.setTotalItems(cartEntity.getCartProducts().stream().mapToInt(CartProductEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartProducts().stream()
                .map(CartProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        cartEntity.persistOrUpdate();

        return cartItemResponse;
    }

    @Override
    public CheckoutResponse checkoutCart() {
        CartEntity cartEntity = CartEntity.findByUserId(UUID.fromString(userSub));

        if(cartEntity == null){
            throw new WebApplicationException("Cannot start checkout with empty cart.", Response.Status.NOT_FOUND);
        }

        CheckoutStartedEventPayload checkoutStartedEventPayload = new CheckoutStartedEventPayload();
        checkoutStartedEventPayload.setCartEntity(cartEntity);
        checkoutStartedEventPayload.setTimestamp(LocalDateTime.now());

        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setMessage("Checkout started sucessfully.");
        checkoutResponse.setOrderId(cartEntity.getId());

        checkoutStartedEmmiter.send(checkoutStartedEventPayload);
        return checkoutResponse;
    }

    @Override
    public DeleteCartProductResponse deleteCartProduct(UUID productId) {
        CartEntity cartEntity = CartEntity.find("cartProducts.productId", productId).firstResult();
        if (cartEntity == null) {
            throw new WebApplicationException("Cart not found for the specified item.", Response.Status.NOT_FOUND);
        }

        cartEntity.getCartProducts().removeIf(item -> item.getProductId().equals(productId));

        cartEntity.setTotalItems(cartEntity.getCartProducts().stream().mapToInt(CartProductEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartProducts().stream()
                .map(CartProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));


        cartEntity.persistOrUpdate();

        DeleteCartProductResponse response = new DeleteCartProductResponse();
        response.setMessage("Cart item successfully deleted.");

        return response;
    }


    @Override
    public EmptyCartResponse emptyCart() {
        CartEntity cartEntity = CartEntity.find("userId", UUID.fromString(userSub)).firstResult();

        if (cartEntity == null) {
            throw new WebApplicationException("Cart not found.", Response.Status.NOT_FOUND);
        }

        cartEntity.delete();

        EmptyCartResponse response = new EmptyCartResponse();
        response.setMessage("Cart has been successfully emptied.");
        return response;
    }


    @Override
    public CartResponse getCartProducts() {
        CartEntity cartEntity = CartEntity.find("userId", UUID.fromString(userSub)).firstResult();

        if (cartEntity == null) {
            CartResponse emptyResponse = new CartResponse();
            emptyResponse.setUserId(UUID.fromString(userSub));
            emptyResponse.setProducts(new ArrayList<>());
            emptyResponse.setTotalProducts(0);
            emptyResponse.setTotalPrice(0f);
            return emptyResponse;
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setUserId(cartEntity.getUserId());
        cartResponse.setTotalProducts(cartEntity.getTotalItems());
        cartResponse.setTotalPrice(cartEntity.getTotalPrice().floatValue());

        List<CartProduct> cartItems = cartEntity.getCartProducts().stream().map(cartProductEntity -> {
            CartProduct cartItem = new CartProduct();
            cartItem.setProductId(cartProductEntity.getProductId());
            cartItem.setProductTitle(cartProductEntity.getProductTitle());
            cartItem.setQuantity(cartProductEntity.getQuantity());
            cartItem.setPrice(cartProductEntity.getProductPrice().floatValue());
            cartItem.setTotalPrice(cartProductEntity.getTotalPrice().floatValue());
            return cartItem;
        }).collect(Collectors.toList());

        cartResponse.setProducts(cartItems);

        return cartResponse;
    }

    @Override
    public CartProductResponse updateCartProduct(UUID productId, UpdateCartProductRequest updateCartProductRequest) {

        CartEntity cartEntity = CartEntity.find("cartProducts.productId", productId).firstResult();

        if (cartEntity == null) {
            throw new WebApplicationException("Cart or cart product not found.", Response.Status.NOT_FOUND);
        }

        CartProductEntity cartProductEntity = cartEntity.getCartProducts().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartProductEntity == null) {
            throw new WebApplicationException("Cart product not found.", Response.Status.NOT_FOUND);
        }

        if (updateCartProductRequest.getQuantity() == null || updateCartProductRequest.getQuantity() < 1) {
            throw new WebApplicationException("Invalid quantity. Quantity must be at least 1.", Response.Status.BAD_REQUEST);
        }

        cartProductEntity.setQuantity(updateCartProductRequest.getQuantity());
        cartProductEntity.setTotalPrice(cartProductEntity.getProductPrice().multiply(BigDecimal.valueOf(updateCartProductRequest.getQuantity())));

        cartEntity.setTotalItems(cartEntity.getCartProducts().stream().mapToInt(CartProductEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartProducts().stream()
                .map(CartProductEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        cartEntity.persistOrUpdate();

        CartProductResponse cartProductResponse = new CartProductResponse();
        cartProductResponse.setProduct(Mapper.map(cartProductEntity));
        return cartProductResponse;
    }

}
