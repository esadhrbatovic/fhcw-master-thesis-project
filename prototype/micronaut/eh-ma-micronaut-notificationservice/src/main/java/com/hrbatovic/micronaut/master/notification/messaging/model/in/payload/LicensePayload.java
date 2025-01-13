package com.hrbatovic.micronaut.master.notification.messaging.model.in.payload;

import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class LicensePayload implements Serializable {

    private UUID serialNumber;

    private UUID productId;

    private UUID userId;

    private UUID orderId;

    private Integer licenseDuration;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    private boolean active;

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public LicensePayload setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public UUID getProductId() {
        return productId;
    }

    public LicensePayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicensePayload setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LicensePayload setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getLicenseDuration() {
        return licenseDuration;
    }

    public LicensePayload setLicenseDuration(Integer licenseDuration) {
        this.licenseDuration = licenseDuration;
        return this;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LicensePayload setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LicensePayload setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public LicensePayload setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("serialNumber", serialNumber)
                .append("productId", productId)
                .append("userId", userId)
                .append("orderId", orderId)
                .append("licenseDuration", licenseDuration)
                .append("issuedAt", issuedAt)
                .append("expiresAt", expiresAt)
                .append("active", active)
                .toString();
    }
}
