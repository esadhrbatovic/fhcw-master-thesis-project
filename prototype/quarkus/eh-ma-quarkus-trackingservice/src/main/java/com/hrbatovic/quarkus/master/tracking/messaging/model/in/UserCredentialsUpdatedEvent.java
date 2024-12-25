package com.hrbatovic.quarkus.master.tracking.messaging.model.in;


import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class UserCredentialsUpdatedEvent implements Serializable {
    UUID id;
    String email;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UserCredentialsUpdatedEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public UserCredentialsUpdatedEvent setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentialsUpdatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UserCredentialsUpdatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UserCredentialsUpdatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UserCredentialsUpdatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public UserCredentialsUpdatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public UserCredentialsUpdatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("email", email)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
