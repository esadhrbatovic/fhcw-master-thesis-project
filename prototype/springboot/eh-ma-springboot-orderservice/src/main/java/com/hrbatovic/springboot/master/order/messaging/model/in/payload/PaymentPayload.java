package com.hrbatovic.springboot.master.order.messaging.model.in.payload;


import java.io.Serializable;
import java.util.UUID;

public class PaymentPayload implements Serializable {

    private UUID orderId;

    public PaymentPayload() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentPayload setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

}
