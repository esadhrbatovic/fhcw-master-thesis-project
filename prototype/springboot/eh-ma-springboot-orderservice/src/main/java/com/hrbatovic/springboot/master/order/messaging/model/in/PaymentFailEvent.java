package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.PaymentPayload;


import java.io.Serializable;

public class PaymentFailEvent implements Serializable {

    private PaymentPayload paymentPayload;

    public PaymentFailEvent() {
    }

    private String message;

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    public PaymentFailEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PaymentFailEvent setMessage(String message) {
        this.message = message;
        return this;
    }

}
