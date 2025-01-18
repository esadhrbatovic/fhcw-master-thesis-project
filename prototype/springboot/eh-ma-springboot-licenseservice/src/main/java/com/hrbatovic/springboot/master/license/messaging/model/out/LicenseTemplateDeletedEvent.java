package com.hrbatovic.springboot.master.license.messaging.model.out;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LicenseTemplateDeletedEvent implements Serializable {

    private UUID productId;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "licenseservice";

    private UUID requestCorrelationId;

    public LicenseTemplateDeletedEvent() {
    }

    public UUID getProductId() {
        return productId;
    }

    public LicenseTemplateDeletedEvent setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LicenseTemplateDeletedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public LicenseTemplateDeletedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicenseTemplateDeletedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LicenseTemplateDeletedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public LicenseTemplateDeletedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }
}
