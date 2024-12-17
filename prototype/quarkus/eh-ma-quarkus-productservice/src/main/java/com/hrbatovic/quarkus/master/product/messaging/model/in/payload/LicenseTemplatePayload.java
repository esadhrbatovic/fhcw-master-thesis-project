package com.hrbatovic.quarkus.master.product.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;

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

    public UUID getProductId() {
        return productId;
    }

    public Integer getLicenseDuration() {
        return licenseDuration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "LicenseTemplateCreatedEvent{" +
                "id=" + id +
                ", productId=" + productId +
                ", licenseDuration=" + licenseDuration +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
