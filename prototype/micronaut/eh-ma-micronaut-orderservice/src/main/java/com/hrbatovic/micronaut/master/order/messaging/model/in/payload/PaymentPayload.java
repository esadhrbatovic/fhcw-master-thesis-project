package com.hrbatovic.micronaut.master.order.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

@Serdeable
public class PaymentPayload implements Serializable {

    private UUID orderId;

    public PaymentPayload() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", orderId)
                .toString();
    }
}
