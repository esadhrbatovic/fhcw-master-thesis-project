package com.hrbatovic.quarkus.master.tracking.messaging.model.in;

import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.OrderPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class OrderCreatedEvent implements Serializable {

    OrderPayload order;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

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

    public OrderCreatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
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
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("order", order)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
