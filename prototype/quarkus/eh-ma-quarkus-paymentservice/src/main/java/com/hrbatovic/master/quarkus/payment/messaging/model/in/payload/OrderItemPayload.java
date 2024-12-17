package com.hrbatovic.master.quarkus.payment.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class OrderItemPayload implements Serializable {

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }


    @Override
    public String toString() {
        return "OrderItemPayload{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
