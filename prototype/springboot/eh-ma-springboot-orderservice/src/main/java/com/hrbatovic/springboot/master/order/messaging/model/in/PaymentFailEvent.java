package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.PaymentPayload;


import java.io.Serializable;

public class PaymentFailEvent implements Serializable {

    private PaymentPayload paymentPayload;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentFailEvent{");
        sb.append("paymentPayload=").append(paymentPayload);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
