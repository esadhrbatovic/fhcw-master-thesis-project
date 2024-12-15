package com.hrbatovic.quarkus.master.cart.messaging.model.out;

import com.hrbatovic.quarkus.master.cart.messaging.model.out.payload.CartPayload;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckoutStartedEvent implements Serializable {

    private CartPayload cart;

    private LocalDateTime timestamp;

    public CheckoutStartedEvent() {
    }

    public CartPayload getCart() {
        return cart;
    }

    public CheckoutStartedEvent setCart(CartPayload cart) {
        this.cart = cart;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public CheckoutStartedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "CheckoutStartedEvent{" +
                "cartEntity=" + cart +
                ", timestamp=" + timestamp +
                '}';
    }


}
