package com.hrbatovic.micronaut.master.order.messaging.model.in;

import com.hrbatovic.micronaut.master.order.messaging.model.in.payload.PaymentPayload;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Serdeable
public class PaymentSuccessEvent implements Serializable {
    private PaymentPayload paymentPayload;

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paymentPayload", paymentPayload)
                .toString();
    }

}
