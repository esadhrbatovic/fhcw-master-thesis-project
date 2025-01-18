package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.PaymentPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class PaymentSuccessEvent implements Serializable {
    private PaymentPayload paymentPayload;

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    public PaymentSuccessEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentSuccessEvent{");
        sb.append("paymentPayload=").append(paymentPayload);
        sb.append('}');
        return sb.toString();
    }
}
