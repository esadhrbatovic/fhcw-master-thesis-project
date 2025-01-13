package com.hrbatovic.micronaut.master.order.messaging.model.in;

import com.hrbatovic.micronaut.master.order.messaging.model.in.payload.PaymentPayload;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Serdeable
public class PaymentFailEvent implements Serializable {

    private PaymentPayload paymentPayload;

    private String message;

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paymentPayload", paymentPayload)
                .append("message", message)
                .toString();
    }
}
