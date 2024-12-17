package com.hrbatovic.quarkus.master.order.messaging.model.in;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class OrderNotificationSentEvent implements Serializable {
    private UUID orderId;

    public OrderNotificationSentEvent() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }


}
