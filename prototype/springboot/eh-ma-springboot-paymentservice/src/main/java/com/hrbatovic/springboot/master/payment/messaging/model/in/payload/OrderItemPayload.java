package com.hrbatovic.springboot.master.payment.messaging.model.in.payload;

import java.io.Serializable;
import java.util.UUID;

public class OrderItemPayload implements Serializable {

    public OrderItemPayload() {
    }

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }

    public OrderItemPayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public OrderItemPayload setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItemPayload setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

}
