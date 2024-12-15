package com.hrbatovic.master.quarkus.payment.messaging.model.out;



import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;

import java.io.Serializable;

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
