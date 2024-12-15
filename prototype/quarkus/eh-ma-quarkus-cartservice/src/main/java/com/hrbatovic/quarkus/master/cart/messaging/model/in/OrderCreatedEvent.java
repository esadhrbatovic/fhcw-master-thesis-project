package com.hrbatovic.quarkus.master.cart.messaging.model.in;

import com.hrbatovic.quarkus.master.cart.messaging.model.in.payload.OrderPayload;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }

    public void setOrder(OrderPayload order) {
        this.order = order;
    }
}