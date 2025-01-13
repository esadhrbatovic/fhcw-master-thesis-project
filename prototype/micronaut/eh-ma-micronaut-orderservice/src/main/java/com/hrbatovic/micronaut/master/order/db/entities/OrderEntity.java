package com.hrbatovic.micronaut.master.order.db.entities;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Serdeable
@MappedEntity(value = "orders")
public class OrderEntity {

    @Id
    private UUID id;

    private UUID userId;

    private String status;

    private String statusDetail;

    private String currency;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID paymentToken;

    private String paymentMethod;

    private List<OrderItemEntity> orderItems;

    public OrderEntity() {
    }

    public UUID getId() {
        return id;
    }

    public OrderEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderEntity setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public OrderEntity setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public OrderEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderEntity setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OrderEntity setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public OrderEntity setPaymentToken(UUID paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public OrderEntity setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("userId", userId)
                .append("status", status)
                .append("statusDetail", statusDetail)
                .append("currency", currency)
                .append("totalAmount", totalAmount)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("paymentToken", paymentToken)
                .append("paymentMethod", paymentMethod)
                .append("orderItems", orderItems)
                .toString();
    }

}
