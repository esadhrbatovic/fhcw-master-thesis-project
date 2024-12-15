package com.hrbatovic.quarkus.master.order.db.entities;


import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Sort;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.codecs.pojo.annotations.BsonId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@MongoEntity(collection = "orders")
public class OrderEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private UUID userId;

    private String status;

    private String statusDetail;

    private String currency;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UUID paymenToken;

    private String paymentMethod;

    private List<OrderItemEntity> orderItems;

    public OrderEntity() {
    }


    public static PanacheQuery<OrderEntity> buildUserQuery(UUID userId, String status, String sort) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("{ 'userId': :userId }");
        params.put("userId", userId);

        if (status != null && !status.isEmpty()) {
            queryBuilder.append(", { 'status': :status }");
            params.put("status", status);
        }

        String fullQuery = "{ '$and': [ " + queryBuilder.toString() + " ] }";
        Sort sortOrder = resolveSortOrder(sort);

        return (sortOrder != null)
                ? find(fullQuery, sortOrder, params)
                : find(fullQuery, params);
    }

    public static PanacheQuery<OrderEntity> buildQuery(String status, String sort) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder();

        if (status != null && !status.isEmpty()) {
            queryBuilder.append("{ 'status': :status }");
            params.put("status", status);
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = resolveSortOrder(sort);
        return (sortOrder != null)
                ? find(queryBuilder.toString(), sortOrder, params)
                : find(queryBuilder.toString(), params);
    }

    private static Sort resolveSortOrder(String sort) {
        if (sort == null || sort.isEmpty()) return null;

        switch (sort) {
            case "dateAsc": return Sort.ascending("createdAt");
            case "dateDesc": return Sort.descending("createdAt");
            case "amountAsc": return Sort.ascending("totalAmount");
            case "amountDesc": return Sort.descending("totalAmount");
            default: return null;
        }
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

    public UUID getPaymenToken() {
        return paymenToken;
    }

    public OrderEntity setPaymenToken(UUID paymenToken) {
        this.paymenToken = paymenToken;
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
                .append("paymenToken", paymenToken)
                .append("paymentMethod", paymentMethod)
                .append("orderItems", orderItems)
                .toString();
    }
}
