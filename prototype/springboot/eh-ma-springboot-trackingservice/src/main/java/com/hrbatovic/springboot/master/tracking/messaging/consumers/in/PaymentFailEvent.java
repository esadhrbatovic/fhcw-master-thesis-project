package com.hrbatovic.springboot.master.tracking.messaging.consumers.in;


import com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload.PaymentPayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentFailEvent implements Serializable {
    public PaymentFailEvent() {
    }

    private PaymentPayload paymentPayload;

    private String message;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private String sourceService;

    private UUID requestCorrelationId;

    public PaymentPayload getPaymentPayload() {
        return paymentPayload;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getSourceService() {
        return sourceService;
    }

    public PaymentFailEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }

    public PaymentFailEvent setMessage(String message) {
        this.message = message;
        return this;
    }

    public PaymentFailEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public PaymentFailEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public PaymentFailEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public PaymentFailEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public PaymentFailEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public PaymentFailEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentFailEvent{");
        sb.append("paymentPayload=").append(paymentPayload);
        sb.append(", message='").append(message).append('\'');
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
