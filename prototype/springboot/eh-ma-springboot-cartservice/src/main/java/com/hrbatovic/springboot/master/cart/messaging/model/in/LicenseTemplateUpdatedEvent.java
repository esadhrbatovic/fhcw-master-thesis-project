package com.hrbatovic.springboot.master.cart.messaging.model.in;

import com.hrbatovic.springboot.master.cart.messaging.model.in.payload.LicenseTemplatePayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LicenseTemplateUpdatedEvent implements Serializable {

    private LicenseTemplatePayload licenseTemplate;

    public LicenseTemplateUpdatedEvent() {
    }

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public LicenseTemplatePayload getLicenseTemplate() {
        return licenseTemplate;
    }

    public LicenseTemplateUpdatedEvent setLicenseTemplate(LicenseTemplatePayload licenseTemplate) {
        this.licenseTemplate = licenseTemplate;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LicenseTemplateUpdatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LicenseTemplateUpdatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicenseTemplateUpdatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LicenseTemplateUpdatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public LicenseTemplateUpdatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    public LicenseTemplateUpdatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

}
