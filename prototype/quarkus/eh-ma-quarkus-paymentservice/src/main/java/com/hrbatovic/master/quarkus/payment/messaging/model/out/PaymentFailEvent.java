package com.hrbatovic.master.quarkus.payment.messaging.model.out;

import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class PaymentFailEvent implements Serializable {

    private OrderPayload order;

    private String message;

    public PaymentFailEvent() {
    }

    public OrderPayload getOrder() {
        return order;
    }

    public PaymentFailEvent setOrder(OrderPayload order) {
        this.order = order;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("order", order)
                .append("message", message)
                .toString();
    }
}
