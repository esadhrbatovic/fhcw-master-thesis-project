package com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LicenseTemplatePayload implements Serializable {

    public LicenseTemplatePayload() {
    }

    private UUID id;

    private UUID productId;

    private Integer licenseDuration;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime timeStamp;


    public UUID getId() {
        return id;
    }

    public LicenseTemplatePayload setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getProductId() {
        return productId;
    }

    public LicenseTemplatePayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public Integer getLicenseDuration() {
        return licenseDuration;
    }

    public LicenseTemplatePayload setLicenseDuration(Integer licenseDuration) {
        this.licenseDuration = licenseDuration;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LicenseTemplatePayload setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LicenseTemplatePayload setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public LicenseTemplatePayload setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LicenseTemplatePayload{");
        sb.append("id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", licenseDuration=").append(licenseDuration);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append('}');
        return sb.toString();
    }
}
