package com.hrbatovic.springboot.master.cart.messaging.model.in.payload;

import java.io.Serializable;
import java.util.UUID;

public class LicenseTemplatePayload implements Serializable {
    private UUID id;

    private UUID productId;

    public LicenseTemplatePayload() {
    }

    public UUID getId() {
        return id;
    }

    public LicenseTemplatePayload setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getProductId() {
        return productId;
    }

    public LicenseTemplatePayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }
}
