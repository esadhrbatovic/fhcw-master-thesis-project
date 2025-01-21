package com.hrbatovic.springboot.master.order.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

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


}
