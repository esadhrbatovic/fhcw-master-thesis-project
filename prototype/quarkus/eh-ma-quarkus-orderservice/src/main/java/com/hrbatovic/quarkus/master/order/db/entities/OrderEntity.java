package com.hrbatovic.quarkus.master.order.db.entities;


import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Sort;
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

    private String currency;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String paymenToken;

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

    public OrderEntity(UUID id) {
        this.id = id;
        this.status = "open";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPaymenToken() {
        return paymenToken;
    }

    public void setPaymenToken(String paymenToken) {
        this.paymenToken = paymenToken;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
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
