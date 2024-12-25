package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.CartProductsApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.cart.api.ShoppingCartCartRestClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class CartApiService implements CartProductsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    ShoppingCartCartRestClient shoppingCartCartRestClient;

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse addProductToCart(AddCartProductRequest addCartProductRequest) {
        return mapper.map(shoppingCartCartRestClient.addProductToCart(mapper.map(addCartProductRequest)));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CheckoutResponse checkoutCart(StartCheckoutRequest startCheckoutRequest) {
        return mapper.map(shoppingCartCartRestClient.checkoutCart(mapper.map(startCheckoutRequest)));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public DeleteCartProductResponse deleteCartProduct(UUID productId) {
        return mapper.map(shoppingCartCartRestClient.deleteCartProduct(productId));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public EmptyCartResponse emptyCart() {
        return mapper.map(shoppingCartCartRestClient.emptyCart());
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartResponse getCartProducts() {
        return mapper.map(shoppingCartCartRestClient.getCartProducts());
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse updateCartProduct(UUID productId, UpdateCartProductRequest updateCartProductRequest) {
        return mapper.map(shoppingCartCartRestClient.updateCartProduct(productId, mapper.map(updateCartProductRequest)));
    }
}
