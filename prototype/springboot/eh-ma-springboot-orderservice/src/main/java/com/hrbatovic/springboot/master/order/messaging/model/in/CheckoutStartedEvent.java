package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.CartPayload;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class CheckoutStartedEvent implements Serializable {

    private CartPayload cart;

    private UUID paymentToken;

    private String paymentMethod;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public CartPayload getCart() {
        return cart;
    }

    public CheckoutStartedEvent setCart(CartPayload cart) {
        this.cart = cart;
        return this;
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public CheckoutStartedEvent setPaymentToken(UUID paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public CheckoutStartedEvent setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public CheckoutStartedEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public CheckoutStartedEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public CheckoutStartedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public CheckoutStartedEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
    }

    public CheckoutStartedEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public CheckoutStartedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CheckoutStartedEvent{");
        sb.append("cart=").append(cart);
        sb.append(", paymentToken=").append(paymentToken);
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
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
