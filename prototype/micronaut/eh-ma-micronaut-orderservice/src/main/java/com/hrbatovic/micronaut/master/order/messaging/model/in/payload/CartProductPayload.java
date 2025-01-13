package com.hrbatovic.micronaut.master.order.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public class CartProductPayload implements Serializable {

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal productPrice;

    private BigDecimal totalPrice;

    public CartProductPayload() {
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
