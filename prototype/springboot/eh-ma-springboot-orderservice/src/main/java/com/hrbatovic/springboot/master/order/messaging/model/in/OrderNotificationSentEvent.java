package com.hrbatovic.springboot.master.order.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

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
