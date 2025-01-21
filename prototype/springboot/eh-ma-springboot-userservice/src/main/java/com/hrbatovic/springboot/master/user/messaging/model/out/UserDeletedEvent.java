package com.hrbatovic.springboot.master.user.messaging.model.out;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserDeletedEvent implements Serializable {

    UUID id;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "userservice";

    private UUID requestCorrelationId;

    public UserDeletedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public UserDeletedEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UserDeletedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UserDeletedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserDeletedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserDeletedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public UserDeletedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

}

