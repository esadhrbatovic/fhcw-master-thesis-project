package com.hrbatovic.quarkus.master.cart.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class LicenseTemplatePayload implements Serializable {

    private UUID id;

    private UUID productId;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("productId", productId)
                .toString();
    }
}
