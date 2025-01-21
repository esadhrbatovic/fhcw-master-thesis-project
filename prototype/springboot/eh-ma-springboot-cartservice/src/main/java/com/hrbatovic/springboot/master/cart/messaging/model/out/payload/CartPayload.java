package com.hrbatovic.springboot.master.cart.messaging.model.out.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartPayload implements Serializable {
    private UUID id;

    private UUID userId;

    private List<CartProductPayload> cartProducts;

    private Integer totalItems;

    private BigDecimal totalPrice;

    public CartPayload() {
    }

    public UUID getId() {
        return id;
    }

    public CartPayload setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public CartPayload setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public List<CartProductPayload> getCartProducts() {
        return cartProducts;
    }

    public CartPayload setCartProducts(List<CartProductPayload> cartProducts) {
        this.cartProducts = cartProducts;
        return this;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public CartPayload setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public CartPayload setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

}
