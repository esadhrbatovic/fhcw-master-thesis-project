package com.hrbatovic.quarkus.master.tracking.messaging.model.in;

import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.UserPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class UserUpdatedEvent implements Serializable {
    private UserPayload userPayload;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public UserUpdatedEvent() {
    }

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserUpdatedEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UserUpdatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UserUpdatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserUpdatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserUpdatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UserUpdatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public UserUpdatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userPayload", userPayload)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
