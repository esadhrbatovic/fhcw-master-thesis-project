package com.hrbatovic.master.quarkus.payment.messaging.model.out.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RegisterForReflection
public class PaymentPayload implements Serializable {

    private UUID userId;

    private UUID orderId;

    private List<PaidItemPayload> paidItemPayloads;

    public UUID getUserId() {
        return userId;
    }

    public PaymentPayload setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentPayload setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<PaidItemPayload> getPaidItemPayloads() {
        return paidItemPayloads;
    }

    public PaymentPayload setPaidItemPayloads(List<PaidItemPayload> paidItemPayloads) {
        this.paidItemPayloads = paidItemPayloads;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", userId)
                .append("orderId", orderId)
                .append("paidItemPayloads", paidItemPayloads)
                .toString();
    }
}
