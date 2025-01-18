package com.hrbatovic.springboot.master.cart.messaging.consumers.model.in;

import java.io.Serializable;
import java.util.UUID;

public class ProductDeletedEvent implements Serializable {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public ProductDeletedEvent setId(UUID id) {
        this.id = id;
        return this;
    }
}
