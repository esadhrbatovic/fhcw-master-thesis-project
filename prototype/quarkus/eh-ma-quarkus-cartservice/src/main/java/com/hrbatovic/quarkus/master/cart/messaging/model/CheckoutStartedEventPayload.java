package com.hrbatovic.quarkus.master.cart.messaging.model;

import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckoutStartedEventPayload implements Serializable {

    private CartEntity cartEntity;

    private LocalDateTime timestamp;

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
