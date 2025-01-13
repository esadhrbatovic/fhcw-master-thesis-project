package com.hrbatovic.micronaut.master.cart.messaging.model.in;

import com.hrbatovic.micronaut.master.cart.messaging.model.in.payload.OrderPayload;
import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
public class OrderCreatedEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }

    public void setOrder(OrderPayload order) {
        this.order = order;
    }

}
