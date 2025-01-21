package com.hrbatovic.springboot.master.payment.messaging.model.out.payload;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PaymentPayload implements Serializable {

    public PaymentPayload() {
    }

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

}
