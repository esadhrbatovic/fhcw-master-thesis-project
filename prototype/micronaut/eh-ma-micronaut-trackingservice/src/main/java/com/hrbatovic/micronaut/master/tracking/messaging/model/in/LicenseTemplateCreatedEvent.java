package com.hrbatovic.micronaut.master.tracking.messaging.model.in;

import com.hrbatovic.micronaut.master.tracking.messaging.model.in.payload.LicenseTemplatePayload;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class LicenseTemplateCreatedEvent implements Serializable {

    private LicenseTemplatePayload licenseTemplate;

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

    public LicenseTemplateCreatedEvent setLicenseTemplate(LicenseTemplatePayload licenseTemplate) {
        this.licenseTemplate = licenseTemplate;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LicenseTemplateCreatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LicenseTemplateCreatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicenseTemplateCreatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LicenseTemplateCreatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public LicenseTemplateCreatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public LicenseTemplateCreatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("licenseTemplate", licenseTemplate)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
