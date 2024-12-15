package com.hrbatovic.master.quarkus.payment.messaging.model.in;

import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }

    public void setOrderEntity(OrderPayload order) {
        this.order = order;
    }


}
