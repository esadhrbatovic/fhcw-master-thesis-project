package com.hrbatovic.quarkus.master.auth.messaging.model.out;


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

    private final String sourceService = "authservice";

    private UUID requestCorrelationId;

    public UserCredentialsUpdatedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public UserCredentialsUpdatedEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserCredentialsUpdatedEvent setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UserCredentialsUpdatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UserCredentialsUpdatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserCredentialsUpdatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserCredentialsUpdatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("email", email)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("requestCorrelationId", requestCorrelationId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .toString();
    }
}
