package com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LicensePayload implements Serializable {

    public LicensePayload() {
    }

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
        final StringBuilder sb = new StringBuilder("LicensePayload{");
        sb.append("serialNumber=").append(serialNumber);
        sb.append(", productId=").append(productId);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", licenseDuration=").append(licenseDuration);
        sb.append(", issuedAt=").append(issuedAt);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
