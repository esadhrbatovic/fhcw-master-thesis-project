package com.hrbatovic.quarkus.master.order.messaging.model;


import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckoutStartedEventPayload implements Serializable {

    private CartEntity cartEntity;

    private String paymentToken;

    private LocalDateTime timestamp;

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
