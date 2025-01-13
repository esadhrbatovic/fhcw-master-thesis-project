package com.hrbatovic.micronaut.master.order.messaging.model.in;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.util.UUID;

@Serdeable
public class LicensesGeneratedEvent implements Serializable {
    private UUID orderId;

    public LicensesGeneratedEvent() {
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
