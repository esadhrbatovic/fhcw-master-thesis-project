package com.hrbatovic.master.quarkus.license.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDateTime;
import java.util.UUID;


@MongoEntity(collection = "licenses")
public class LicenseEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID serialNumber;

    private UUID productId;

    private UUID userId;

    private Integer licenseDuration;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "LicenseEntity{" +
                "serialNumber=" + serialNumber +
                ", productId=" + productId +
                ", userId=" + userId +
                ", licenseDuration=" + licenseDuration +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                ", active=" + active +
                '}';
    }
}
