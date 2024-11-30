package com.hrbatovic.quarkus.master.notification.messaging.model;

import java.io.Serializable;
import java.util.UUID;

public class OrderNotificationSentEventPayload implements Serializable {
    private UUID orderId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderNotificationSentEventPayload{" +
                "orderId=" + orderId +
                '}';
    }
}
