package com.hrbatovic.master.quarkus.license.messaging.model.in;

import com.hrbatovic.master.quarkus.license.messaging.model.in.payload.OrderPayload;

import java.io.Serializable;
import java.util.UUID;

public class PaymentSuccessEvent implements Serializable {

    private OrderPayload order;

    public PaymentSuccessEvent() {
    }


    public OrderPayload getOrder() {
        return order;
    }

    public PaymentSuccessEvent setOrder(OrderPayload order) {
        this.order = order;
        return this;
    }
}