package com.hrbatovic.springboot.master.order.messaging.model.out;

import com.hrbatovic.springboot.master.order.messaging.model.out.payload.OrderPayload;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderCreatedEvent implements Serializable {

    OrderPayload order;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "orderservice";

    private UUID requestCorrelationId;

    public OrderCreatedEvent() {
    }

    public OrderPayload getOrder() {
        return order;
    }

    public OrderCreatedEvent setOrder(OrderPayload order) {
        this.order = order;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderCreatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public OrderCreatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderCreatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public OrderCreatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public OrderCreatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderCreatedEvent{");
        sb.append("order=").append(order);
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
