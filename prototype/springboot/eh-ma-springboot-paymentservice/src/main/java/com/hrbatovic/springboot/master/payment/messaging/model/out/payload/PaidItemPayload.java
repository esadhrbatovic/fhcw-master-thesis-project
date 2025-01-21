package com.hrbatovic.springboot.master.payment.messaging.model.out.payload;

import java.io.Serializable;
import java.util.UUID;

public class PaidItemPayload implements Serializable {

    public PaidItemPayload() {
    }

    private UUID productId;
    private String productTitle;
    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }

    public PaidItemPayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public PaidItemPayload setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaidItemPayload setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

}
