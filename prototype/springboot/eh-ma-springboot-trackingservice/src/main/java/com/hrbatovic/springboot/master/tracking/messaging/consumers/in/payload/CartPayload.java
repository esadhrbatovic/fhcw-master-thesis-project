package com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartPayload implements Serializable {

    public CartPayload() {
    }

    private UUID id;

    private UUID userId;

    private List<CartProductPayload> cartProducts;

    private Integer totalItems;

    private BigDecimal totalPrice;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CartPayload{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", cartProducts=").append(cartProducts);
        sb.append(", totalItems=").append(totalItems);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append('}');
        return sb.toString();
    }
}
