package com.hrbatovic.micronaut.master.order.messaging.model.in;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.util.UUID;

@Serdeable
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
