package com.hrbatovic.springboot.master.cart.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class ProductDeletedEvent implements Serializable {
    private UUID id;

    public ProductDeletedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public ProductDeletedEvent setId(UUID id) {
        this.id = id;
        return this;
    }
}
