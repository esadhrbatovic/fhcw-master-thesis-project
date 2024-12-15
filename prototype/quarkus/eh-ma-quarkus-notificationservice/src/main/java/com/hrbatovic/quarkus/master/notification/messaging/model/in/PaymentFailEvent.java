package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import com.hrbatovic.quarkus.master.notification.messaging.model.in.payload.OrderPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class PaymentFailEvent implements Serializable {

    private OrderPayload order;

    private String message;

    public OrderPayload getOrder() {
        return order;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("order", order)
                .append("message", message)
                .toString();
    }
}
