package com.hrbatovic.micronaut.master.license.messaging.model.in.payload;

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

    public String getProductTitle() {
        return productTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("productId", productId)
                .append("productTitle", productTitle)
                .append("quantity", quantity)
                .toString();
    }
}
