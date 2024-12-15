package com.hrbatovic.master.quarkus.payment.messaging.model.in.payload;

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

    private String paymenToken;

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

    public String getPaymenToken() {
        return paymenToken;
    }

    public OrderPayload setPaymenToken(String paymenToken) {
        this.paymenToken = paymenToken;
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
        return "OrderPayload{" +
                "id=" + id +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", currency='" + currency + '\'' +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", paymenToken='" + paymenToken + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
