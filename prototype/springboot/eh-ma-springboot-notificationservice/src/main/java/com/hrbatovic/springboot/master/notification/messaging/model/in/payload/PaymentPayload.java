package com.hrbatovic.springboot.master.notification.messaging.model.in.payload;

import java.io.Serializable;
import java.util.UUID;

public class PaymentPayload implements Serializable {

    public PaymentPayload() {
    }

    private UUID userId;
    private UUID orderId;

    public UUID getUserId() {
        return userId;
    }

    public PaymentPayload setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentPayload setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

}
