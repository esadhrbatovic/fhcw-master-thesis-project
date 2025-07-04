package com.hrbatovic.micronaut.master.tracking.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

@Serdeable
public class PaidItemPayload implements Serializable {
    private UUID productId;
    private String productTitle;
    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }

    public PaidItemPayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public PaidItemPayload setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaidItemPayload setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("productId", productId)
                .append("productTitle", productTitle)
                .append("quantity", quantity)
                .toString();
    }
}
