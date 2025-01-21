package com.hrbatovic.springboot.master.product.messaging.model.out;

import com.hrbatovic.springboot.master.product.messaging.model.out.payload.ProductPayload;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductCreatedEvent implements Serializable {

    ProductPayload product;

    public ProductCreatedEvent() {
    }

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

}
