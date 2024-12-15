package com.hrbatovic.quarkus.master.order.messaging.model.out;

import com.hrbatovic.quarkus.master.order.messaging.model.out.payload.OrderPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

public class OrderCreatedEvent implements Serializable {

    OrderPayload order;

    public OrderCreatedEvent() {
    }

    public OrderPayload getOrder() {
        return order;
    }

    public OrderCreatedEvent setOrder(OrderPayload order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("order", order)
                .toString();
    }
}
