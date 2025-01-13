package com.hrbatovic.micronaut.master.order.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Serdeable
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

    public UUID getUserId() {
        return userId;
    }

    public List<CartProductPayload> getCartProducts() {
        return cartProducts;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
