package com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RegisterForReflection
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("userId", userId)
                .append("cartProducts", cartProducts)
                .append("totalItems", totalItems)
                .append("totalPrice", totalPrice)
                .toString();
    }
}
