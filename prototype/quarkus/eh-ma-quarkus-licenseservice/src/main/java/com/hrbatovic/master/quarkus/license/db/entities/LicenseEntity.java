package com.hrbatovic.master.quarkus.license.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDateTime;
import java.util.*;

@MongoEntity(collection = "licenses")
public class LicenseEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID serialNumber;

    private UUID productId;

    private String productTitle;

    private UUID userId;

    private UUID orderId;

    private Integer licenseDuration;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public static PanacheQuery<LicenseEntity> queryLicenses(UUID userId, UUID productId, String sort, Integer page, Integer limit) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        if (userId != null) {
            conditions.add("{ 'userId': :userId }");
            params.put("userId", userId);
        }
        if (productId != null) {
            conditions.add("{ 'productId': :productId }");
            params.put("productId", productId);
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("{ '$and': [ ").append(String.join(", ", conditions)).append(" ] }");
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = resolveSortOrder(sort);

        PanacheQuery<LicenseEntity> query = sortOrder != null
                ? LicenseEntity.find(queryBuilder.toString(), sortOrder, params)
                : LicenseEntity.find(queryBuilder.toString(), params);

        Page pagination = Page.of((page != null && page > 0) ? page - 1 : 0, (limit != null && limit > 0) ? limit : 10);
        query.page(pagination);

        return query;
    }

    private static Sort resolveSortOrder(String sort) {
        if (sort == null || sort.isEmpty()) {
            return null;
        }
        return switch (sort) {
            case "dateAsc" -> Sort.ascending("issuedAt");
            case "dateDesc" -> Sort.descending("issuedAt");
            default -> null;
        };
    }

    public LicenseEntity() {
        this.serialNumber = UUID.randomUUID();
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public LicenseEntity setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getLicenseDuration() {
        return licenseDuration;
    }

    public void setLicenseDuration(Integer licenseDuration) {
        this.licenseDuration = licenseDuration;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("serialNumber", serialNumber)
                .append("productId", productId)
                .append("productTitle", productTitle)
                .append("userId", userId)
                .append("orderId", orderId)
                .append("licenseDuration", licenseDuration)
                .append("issuedAt", issuedAt)
                .append("expiresAt", expiresAt)
                .toString();
    }
}
