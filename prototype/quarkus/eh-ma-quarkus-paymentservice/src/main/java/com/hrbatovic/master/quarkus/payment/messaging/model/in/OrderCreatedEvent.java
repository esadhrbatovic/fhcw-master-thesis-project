package com.hrbatovic.master.quarkus.payment.messaging.model.in;

import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

    private OrderPayload order;

    public OrderPayload getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("order", order)
                .toString();
    }
}
