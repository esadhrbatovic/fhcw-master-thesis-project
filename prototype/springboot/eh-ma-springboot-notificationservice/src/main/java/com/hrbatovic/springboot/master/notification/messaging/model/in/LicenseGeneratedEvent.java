package com.hrbatovic.springboot.master.notification.messaging.model.in;

import com.hrbatovic.springboot.master.notification.messaging.model.in.payload.LicensePayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class LicenseGeneratedEvent implements Serializable {

    public LicenseGeneratedEvent() {
    }

    private UUID orderId;
    private UUID userId;

    private List<LicensePayload> licenses;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public UUID getOrderId() {
        return orderId;
    }

    public LicenseGeneratedEvent setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicenseGeneratedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public List<LicensePayload> getLicenses() {
        return licenses;
    }

    public LicenseGeneratedEvent setLicenses(List<LicensePayload> licenses) {
        this.licenses = licenses;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LicenseGeneratedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LicenseGeneratedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LicenseGeneratedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public LicenseGeneratedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public LicenseGeneratedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }
}

