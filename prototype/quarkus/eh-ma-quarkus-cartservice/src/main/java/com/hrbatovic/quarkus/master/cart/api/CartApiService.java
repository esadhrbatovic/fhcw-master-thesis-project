package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.CartApi;
import com.hrbatovic.master.quarkus.cart.model.*;
import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.CartItemEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import com.hrbatovic.quarkus.master.cart.mapper.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class CartApiService implements CartApi {

    @Inject
    @Claim(standard = Claims.sub)
    String userSub;

    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest addCartItemRequest) {
        CartEntity cartEntity = CartEntity.findByUserId(UUID.fromString(userSub));
        CartItemResponse cartItemResponse = new CartItemResponse();

        ProductEntity productEntity = ProductEntity.findById(addCartItemRequest.getProductId());
        if (productEntity == null) {
            throw new WebApplicationException("Product not found.", Response.Status.NOT_FOUND);
        }

        if (cartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setUserId(UUID.fromString(userSub));
            cartEntity.setCartItems(new ArrayList<>());
            cartEntity.setTotalItems(0);
            cartEntity.setTotalPrice(BigDecimal.ZERO);
        }

        CartItemEntity existingCartItem = cartEntity.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productEntity.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + addCartItemRequest.getQuantity());
            existingCartItem.setTotalPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));

            cartItemResponse.setMessage("Product quantity updated in cart.");
            cartItemResponse.setItem(Mapper.map(existingCartItem));
        } else {
            CartItemEntity newCartItem = new CartItemEntity();
            newCartItem.setProductId(productEntity.getId());
            newCartItem.setQuantity(addCartItemRequest.getQuantity());
            newCartItem.setTotalPrice(productEntity.getPrice().multiply(BigDecimal.valueOf(addCartItemRequest.getQuantity())));
            newCartItem.setProductTitle(productEntity.getTitle());
            newCartItem.setProductPrice(productEntity.getPrice());

            cartEntity.getCartItems().add(newCartItem);

            cartItemResponse.setMessage("Product added to cart.");
            cartItemResponse.setItem(Mapper.map(newCartItem));
        }

        cartEntity.setTotalItems(cartEntity.getCartItems().stream().mapToInt(CartItemEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        cartEntity.persistOrUpdate();

        return cartItemResponse;
    }

    @Override
    public CheckoutResponse checkoutCart() {
        //TODO: produce kafka event to initiaze checkout
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(UUID itemId) {
        CartEntity cartEntity = CartEntity.find("cartItems._id", itemId).firstResult();
        if (cartEntity == null) {
            throw new WebApplicationException("Cart not found for the specified item.", Response.Status.NOT_FOUND);
        }

        cartEntity.getCartItems().removeIf(item -> item.getId().equals(itemId));

        cartEntity.setTotalItems(cartEntity.getCartItems().stream().mapToInt(CartItemEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));


        cartEntity.persistOrUpdate();


        DeleteCartItemResponse response = new DeleteCartItemResponse();
        response.setMessage("Cart item successfully deleted.");

        return response;
    }


    @Override
    public EmptyCartResponse emptyCart() {
        CartEntity cartEntity = CartEntity.find("userId", UUID.fromString(userSub)).firstResult();

        if (cartEntity == null) {
            throw new WebApplicationException("Cart not found.", Response.Status.NOT_FOUND);
        }

        if (cartEntity.getCartItems() != null) {
            cartEntity.getCartItems().forEach(CartItemEntity::delete);
        }

        cartEntity.delete();

        EmptyCartResponse response = new EmptyCartResponse();
        response.setMessage("Cart has been successfully emptied.");
        return response;
    }


    @Override
    public CartResponse getCartItems() {
        CartEntity cartEntity = CartEntity.find("userId", UUID.fromString(userSub)).firstResult();

        if (cartEntity == null) {
            CartResponse emptyResponse = new CartResponse();
            emptyResponse.setUserId(UUID.fromString(userSub));
            emptyResponse.setItems(new ArrayList<>());
            emptyResponse.setTotalItems(0);
            emptyResponse.setTotalPrice(0f);
            return emptyResponse;
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setUserId(cartEntity.getUserId());
        cartResponse.setTotalItems(cartEntity.getTotalItems());
        cartResponse.setTotalPrice(cartEntity.getTotalPrice().floatValue());

        List<CartItem> cartItems = cartEntity.getCartItems().stream().map(cartItemEntity -> {
            CartItem cartItem = new CartItem();
            cartItem.setItemId(cartItemEntity.getId());
            cartItem.setProductId(cartItemEntity.getProductId());
            cartItem.setProductTitle(cartItemEntity.getProductTitle());
            cartItem.setQuantity(cartItemEntity.getQuantity());
            cartItem.setPrice(cartItemEntity.getProductPrice().floatValue());
            cartItem.setTotalPrice(cartItemEntity.getTotalPrice().floatValue());
            return cartItem;
        }).collect(Collectors.toList());

        cartResponse.setItems(cartItems);

        return cartResponse;
    }

    @Override
    public CartItemResponse updateCartItem(UUID itemId, UpdateCartItemRequest updateCartItemRequest) {

        CartEntity cartEntity = CartEntity.find("cartItems._id", itemId).firstResult();

        if (cartEntity == null) {
            throw new WebApplicationException("Cart or cart item not found.", Response.Status.NOT_FOUND);
        }

        CartItemEntity cartItemEntity = cartEntity.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElse(null);

        if (cartItemEntity == null) {
            throw new WebApplicationException("Cart item not found.", Response.Status.NOT_FOUND);
        }

        if (updateCartItemRequest.getQuantity() == null || updateCartItemRequest.getQuantity() < 1) {
            throw new WebApplicationException("Invalid quantity. Quantity must be at least 1.", Response.Status.BAD_REQUEST);
        }

        cartItemEntity.setQuantity(updateCartItemRequest.getQuantity());
        cartItemEntity.setTotalPrice(cartItemEntity.getProductPrice().multiply(BigDecimal.valueOf(updateCartItemRequest.getQuantity())));

        cartEntity.setTotalItems(cartEntity.getCartItems().stream().mapToInt(CartItemEntity::getQuantity).sum());
        cartEntity.setTotalPrice(cartEntity.getCartItems().stream()
                .map(CartItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        cartEntity.persistOrUpdate();

        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setItem(Mapper.map(cartItemEntity));
        return cartItemResponse;
    }

}
