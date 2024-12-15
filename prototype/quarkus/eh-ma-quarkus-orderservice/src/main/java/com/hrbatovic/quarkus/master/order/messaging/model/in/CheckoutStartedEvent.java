package com.hrbatovic.quarkus.master.order.messaging.model.in;

import com.hrbatovic.quarkus.master.order.messaging.model.in.payload.CartPayload;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckoutStartedEvent implements Serializable {

    private CartPayload cart;

    private String paymentToken;

    private LocalDateTime timestamp;

    public CartPayload getCart() {
        return cart;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPaymentToken() {
        return paymentToken;
    }
}
