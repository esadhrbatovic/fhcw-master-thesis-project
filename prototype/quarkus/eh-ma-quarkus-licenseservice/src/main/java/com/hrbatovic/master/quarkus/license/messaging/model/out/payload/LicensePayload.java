package com.hrbatovic.master.quarkus.license.messaging.model.out.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class LicensePayload implements Serializable {

    private UUID serialNumber;

    private UUID productId;

    private UUID userId;

    private UUID orderId;

    private Integer licenseDuration;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;


    public LicensePayload() {
    }

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
}
