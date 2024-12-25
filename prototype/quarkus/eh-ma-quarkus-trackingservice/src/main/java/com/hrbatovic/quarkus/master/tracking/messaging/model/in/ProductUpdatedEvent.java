package com.hrbatovic.quarkus.master.tracking.messaging.model.in;


import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.ProductPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class ProductUpdatedEvent {

    ProductPayload product;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public ProductPayload getProduct() {
        return product;
    }

    public ProductUpdatedEvent setProduct(ProductPayload product) {
        this.product = product;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ProductUpdatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public ProductUpdatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public ProductUpdatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProductUpdatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public ProductUpdatedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public ProductUpdatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("product", product)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
