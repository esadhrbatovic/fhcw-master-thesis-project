package com.hrbatovic.micronaut.master.payment.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Serdeable
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

    public OrderPayload setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public OrderPayload setPaymentToken(UUID paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public OrderPayload setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public List<OrderItemPayload> getOrderItems() {
        return orderItems;
    }

    public OrderPayload setOrderItems(List<OrderItemPayload> orderItems) {
        this.orderItems = orderItems;
        return this;
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
