package com.hrbatovic.springboot.master.license.db.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "licenses")
public class LicenseEntity {
    @Id
    private UUID serialNumber;

    private UUID productId;

    private String productTitle;

    private UUID userId;

    private UUID orderId;

    private Integer licenseDuration;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;


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
