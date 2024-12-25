package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import com.hrbatovic.quarkus.master.notification.messaging.model.in.payload.LicensePayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RegisterForReflection
public class LicenseGeneratedEvent implements Serializable {
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

    public UUID getUserId() {
        return userId;
    }

    public List<LicensePayload> getLicenses() {
        return licenses;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", orderId)
                .append("userId", userId)
                .append("licenses", licenses)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
