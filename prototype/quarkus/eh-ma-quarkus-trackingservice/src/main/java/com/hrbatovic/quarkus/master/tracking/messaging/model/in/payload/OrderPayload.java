package com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderPayload implements Serializable {

    private UUID id;

    private UUID userId;

    private String status;

    private String currency;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID paymenToken;

    private String paymentMethodSelector;

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

    public String getStatus() {
        return status;
    }

    public OrderPayload setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public OrderPayload setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderPayload setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderPayload setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OrderPayload setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UUID getPaymenToken() {
        return paymenToken;
    }

    public OrderPayload setPaymenToken(UUID paymenToken) {
        this.paymenToken = paymenToken;
        return this;
    }

    public String getPaymentMethodSelector() {
        return paymentMethodSelector;
    }

    public OrderPayload setPaymentMethodSelector(String paymentMethodSelector) {
        this.paymentMethodSelector = paymentMethodSelector;
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
                .append("status", status)
                .append("currency", currency)
                .append("totalAmount", totalAmount)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("paymenToken", paymenToken)
                .append("paymentMethodSelector", paymentMethodSelector)
                .append("orderItems", orderItems)
                .toString();
    }
}
