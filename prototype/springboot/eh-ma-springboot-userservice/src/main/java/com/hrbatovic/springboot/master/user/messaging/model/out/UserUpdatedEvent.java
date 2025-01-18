package com.hrbatovic.springboot.master.user.messaging.model.out;

import com.hrbatovic.springboot.master.user.messaging.model.common.payload.UserPayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserUpdatedEvent implements Serializable {

    private UserPayload userPayload;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "userservice";

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

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public UserUpdatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserUpdatedEvent{");
        sb.append("userPayload=").append(userPayload);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", userId=").append(userId);
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", sourceService='").append(sourceService).append('\'');
        sb.append(", requestCorrelationId=").append(requestCorrelationId);
        sb.append('}');
        return sb.toString();
    }
}
