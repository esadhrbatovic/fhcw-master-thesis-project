package com.hrbatovic.quarkus.master.order.messaging.model.in;

import com.hrbatovic.quarkus.master.order.messaging.model.out.payload.OrderPayload;

import java.io.Serializable;

public class PaymentSuccessEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }
}