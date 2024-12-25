package com.hrbatovic.quarkus.master.product.messaging.model.out;

import com.hrbatovic.quarkus.master.product.messaging.model.out.payload.ProductPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class ProductCreatedEvent implements Serializable {
    ProductPayload product;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "productservice";

    private UUID requestCorrelationId;

    public ProductPayload getProduct() {
        return product;
    }

    public ProductCreatedEvent setProduct(ProductPayload product) {
        this.product = product;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ProductCreatedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public ProductCreatedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public ProductCreatedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProductCreatedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public ProductCreatedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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
