package com.hrbatovic.micronaut.master.license.messaging.model.out;

import com.hrbatovic.micronaut.master.license.messaging.model.out.payload.LicensePayload;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Serdeable
public class LicensesGeneratedEvent implements Serializable {
    private UUID orderId;
    private List<LicensePayload> licenses;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "licenseservice";

    private UUID requestCorrelationId;

    public UUID getOrderId() {
        return orderId;
    }

    public LicensesGeneratedEvent setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public List<LicensePayload> getLicenses() {
        return licenses;
    }

    public LicensesGeneratedEvent setLicenses(List<LicensePayload> licenses) {
        this.licenses = licenses;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LicensesGeneratedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LicensesGeneratedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicensesGeneratedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LicensesGeneratedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public LicensesGeneratedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("orderId", orderId)
                .append("licenses", licenses)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
