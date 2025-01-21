package com.hrbatovic.springboot.master.license.messaging.model.out;

import com.hrbatovic.springboot.master.license.messaging.model.out.payload.LicensePayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class LicensesGeneratedEvent implements Serializable {

    public LicensesGeneratedEvent() {
    }

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

}
