package com.hrbatovic.quarkus.master.auth.messaging.model.out;

import com.hrbatovic.quarkus.master.auth.messaging.model.out.payload.UserPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class UserRegisteredEvent implements Serializable {

    private UserPayload userPayload;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "authservice";

    private UUID requestCorrelationId;


    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserRegisteredEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UserRegisteredEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UserRegisteredEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserRegisteredEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserRegisteredEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public UserRegisteredEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userPayload", userPayload)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("requestCorrelationId", requestCorrelationId)
                .append("sourceService", sourceService)
                .toString();
    }
}