package com.hrbatovic.quarkus.master.tracking.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.UUID;

public class MetadataEntity extends PanacheMongoEntityBase {

    private UUID userId;

    private UUID productId;

    private UUID orderId;

    private String userMail;

    private UUID sesionId;

    private String sourceService;

    private LocalDateTime timestamp;

    public UUID getUserId() {
        return userId;
    }

    public MetadataEntity setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getProductId() {
        return productId;
    }

    public MetadataEntity setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public MetadataEntity setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserMail() {
        return userMail;
    }

    public MetadataEntity setUserMail(String userMail) {
        this.userMail = userMail;
        return this;
    }

    public UUID getSesionId() {
        return sesionId;
    }

    public MetadataEntity setSesionId(UUID sesionId) {
        this.sesionId = sesionId;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public MetadataEntity setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MetadataEntity setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userId", userId)
                .append("productId", productId)
                .append("orderId", orderId)
                .append("userMail", userMail)
                .append("sesionId", sesionId)
                .append("sourceService", sourceService)
                .append("timestamp", timestamp)
                .toString();
    }
}
