package com.hrbatovic.springboot.master.product.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class LicenseTemplateDeletedEvent implements Serializable {

    private UUID id;

    public LicenseTemplateDeletedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public LicenseTemplateDeletedEvent setId(UUID id) {
        this.id = id;
        return this;
    }
}
