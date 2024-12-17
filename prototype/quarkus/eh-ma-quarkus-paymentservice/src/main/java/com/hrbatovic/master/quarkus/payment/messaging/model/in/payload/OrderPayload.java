package com.hrbatovic.master.quarkus.payment.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RegisterForReflection
public class OrderPayload implements Serializable {

    private UUID id;

    private UUID userId;

    private UUID paymentToken;

    private String paymentMethod;

    private List<OrderItemPayload> orderItems;

    public OrderPayload() {
    }

    public UUID getId() {
        return id;
    }

    public OrderPayload setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<OrderItemPayload> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("userId", userId)
                .append("paymentToken", paymentToken)
                .append("paymentMethod", paymentMethod)
                .append("orderItems", orderItems)
                .toString();
    }
}
