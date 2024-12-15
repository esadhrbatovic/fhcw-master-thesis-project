package com.hrbatovic.quarkus.master.order.messaging.model.out;

import com.hrbatovic.quarkus.master.order.messaging.model.out.payload.OrderPayload;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

    OrderPayload order;

    public OrderCreatedEvent() {
    }

    public OrderPayload getOrder() {
        return order;
    }

    public OrderCreatedEvent setOrder(OrderPayload order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "order=" + order +
                '}';
    }
}
