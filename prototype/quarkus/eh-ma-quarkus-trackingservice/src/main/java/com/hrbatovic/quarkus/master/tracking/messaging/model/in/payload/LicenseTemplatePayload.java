package com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class LicenseTemplatePayload implements Serializable {
    private UUID id;

    private UUID productId;

    private Integer licenseDuration;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime timeStamp;

    public LicenseTemplatePayload() {
    }

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
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("productId", productId)
                .append("licenseDuration", licenseDuration)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("timeStamp", timeStamp)
                .toString();
    }
}
