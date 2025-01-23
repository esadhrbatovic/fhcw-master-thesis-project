package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.ShoppingCartApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
public class CartApiService implements ShoppingCartApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.cart.api.ShoppingCartApi shoppingCartApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse addProductToCart(String authorization, AddCartProductRequest addCartProductRequest) {
        return mapper.map(shoppingCartApiClient.addProductToCart(authorization, mapper.map(addCartProductRequest)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public CheckoutResponse checkoutCart(String authorization, StartCheckoutRequest startCheckoutRequest) {
        return mapper.map(shoppingCartApiClient.checkoutCart(authorization, mapper.map(startCheckoutRequest)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public DeleteCartProductResponse deleteCartProduct(UUID productId, String authorization) {
        return mapper.map(shoppingCartApiClient.deleteCartProduct(productId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public EmptyCartResponse emptyCart(String authorization) {
        return mapper.map(shoppingCartApiClient.emptyCart(authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public CartResponse getCartProducts(String authorization) {
        return mapper.map(shoppingCartApiClient.getCartProducts(authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public CartProductResponse updateCartProduct(UUID productId, String authorization, UpdateCartProductRequest updateCartProductRequest) {
        return mapper.map(shoppingCartApiClient.updateCartProduct(productId, authorization, mapper.map(updateCartProductRequest)));
    }
}
