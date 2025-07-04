package com.hrbatovic.master.quarkus.license.messaging.model.out;

import com.hrbatovic.master.quarkus.license.messaging.model.out.payload.LicenseTemplatePayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class LicenseTemplateUpdatedEvent implements Serializable {

    private LicenseTemplatePayload licenseTemplate;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "licenseservice";

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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

