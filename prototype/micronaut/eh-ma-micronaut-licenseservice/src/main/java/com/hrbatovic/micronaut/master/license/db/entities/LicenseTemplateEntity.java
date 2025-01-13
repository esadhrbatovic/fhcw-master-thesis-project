package com.hrbatovic.micronaut.master.license.db.entities;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
@MappedEntity(value = "license-templates")
public class LicenseTemplateEntity {

    @Id
    private UUID id;

    private UUID productId;

    private Integer licenseDuration;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public LicenseTemplateEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getLicenseDuration() {
        return licenseDuration;
    }

    public void setLicenseDuration(Integer licenseDuration) {
        this.licenseDuration = licenseDuration;
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

    @Override
    public String toString() {
        return "LicenseTemplateEntity{" +
                "id=" + id +
                ", productId=" + productId +
                ", licenseDuration=" + licenseDuration +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
