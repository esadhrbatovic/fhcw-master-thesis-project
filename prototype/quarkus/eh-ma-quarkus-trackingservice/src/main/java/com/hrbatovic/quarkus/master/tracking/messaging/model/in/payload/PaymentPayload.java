package com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PaymentPayload implements Serializable {
    private UUID userId;
    private UUID orderId;
    private List<PaidItemPayload> paidItemPayloads;

    public UUID getUserId() {
        return userId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public List<PaidItemPayload> getPaidItemPayloads() {
        return paidItemPayloads;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userId", userId)
                .append("orderId", orderId)
                .append("paidItemPayloads", paidItemPayloads)
                .toString();
    }
}
