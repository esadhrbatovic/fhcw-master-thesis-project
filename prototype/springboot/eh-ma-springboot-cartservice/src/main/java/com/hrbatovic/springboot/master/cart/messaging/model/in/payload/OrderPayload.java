package com.hrbatovic.springboot.master.cart.messaging.model.in.payload;

import java.io.Serializable;
import java.util.UUID;

public class OrderPayload implements Serializable {

    private UUID id;

    public OrderPayload() {
    }

    public UUID getId() {
        return id;
    }

    public OrderPayload setId(UUID id) {
        this.id = id;
        return this;
    }
}
