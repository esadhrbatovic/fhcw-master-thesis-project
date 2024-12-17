package com.hrbatovic.quarkus.master.cart.messaging.model.in;

import com.hrbatovic.quarkus.master.cart.messaging.model.in.payload.OrderPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public class OrderCreatedEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }

    public void setOrder(OrderPayload order) {
        this.order = order;
    }
}