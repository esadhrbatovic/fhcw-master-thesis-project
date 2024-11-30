package com.hrbatovic.quarkus.master.order.messaging.model;

import java.io.Serializable;
import java.util.UUID;

public class LicensesGeneratedEventPayload implements Serializable {
    private UUID orderId;

    public LicensesGeneratedEventPayload() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "LicensesGeneratedEventPayload{" +
                "orderId=" + orderId +
                '}';
    }
}
