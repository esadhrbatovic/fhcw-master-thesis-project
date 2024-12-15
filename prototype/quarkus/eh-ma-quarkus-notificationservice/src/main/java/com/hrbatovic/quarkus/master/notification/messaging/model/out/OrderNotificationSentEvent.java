package com.hrbatovic.quarkus.master.notification.messaging.model.out;

import java.io.Serializable;
import java.util.UUID;

public class OrderNotificationSentEvent implements Serializable {

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
