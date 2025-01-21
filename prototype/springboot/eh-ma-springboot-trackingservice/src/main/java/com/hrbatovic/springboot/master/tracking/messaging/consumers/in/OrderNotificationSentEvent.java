package com.hrbatovic.springboot.master.tracking.messaging.consumers.in;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderNotificationSentEvent implements Serializable {

    public OrderNotificationSentEvent() {
    }

    private UUID orderId;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderNotificationSentEvent setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderNotificationSentEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public OrderNotificationSentEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderNotificationSentEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public OrderNotificationSentEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public OrderNotificationSentEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public OrderNotificationSentEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderNotificationSentEvent{");
        sb.append("orderId=").append(orderId);
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
