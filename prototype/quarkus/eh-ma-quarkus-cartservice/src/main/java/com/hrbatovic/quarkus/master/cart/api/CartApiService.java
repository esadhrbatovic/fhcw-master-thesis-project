package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.CartApi;
import com.hrbatovic.master.quarkus.cart.model.*;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class CartApiService implements CartApi {

    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest addCartItemRequest) {
        return null;
    }

    @Override
    public CheckoutResponse checkoutCart() {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(UUID itemId) {
        return null;
    }

    @Override
    public EmptyCartResponse emptyCart() {
        return null;
    }

    @Override
    public CartResponse getCartItems() {
        return null;
    }

    @Override
    public CartItemResponse updateCartItem(UUID itemId, UpdateCartItemRequest updateCartItemRequest) {
        return null;
    }
}
