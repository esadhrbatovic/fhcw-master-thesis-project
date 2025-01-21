package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.PaymentPayload;

import java.io.Serializable;

public class PaymentSuccessEvent implements Serializable {

    private PaymentPayload paymentPayload;

    public PaymentSuccessEvent() {
    }

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    public PaymentSuccessEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }
}

