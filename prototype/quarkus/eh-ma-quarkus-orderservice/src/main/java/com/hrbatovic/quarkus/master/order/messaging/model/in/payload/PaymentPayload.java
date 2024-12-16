package com.hrbatovic.quarkus.master.order.messaging.model.in.payload;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

public class PaymentPayload implements Serializable {

    private UUID orderId;

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
