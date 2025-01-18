package com.hrbatovic.springboot.master.cart.messaging.consumers.model.in;

import com.hrbatovic.springboot.master.cart.messaging.consumers.model.in.payload.OrderPayload;

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
