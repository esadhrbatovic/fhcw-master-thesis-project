package com.hrbatovic.micronaut.master.cart.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.util.UUID;

@Serdeable
public class OrderPayload implements Serializable {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public OrderPayload setId(UUID id) {
        this.id = id;
        return this;
    }
}
