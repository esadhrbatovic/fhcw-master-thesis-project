package com.hrbatovic.springboot.master.tracking.messaging.consumers.in;

import com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload.PaymentPayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentSuccessEvent implements Serializable {

    public PaymentSuccessEvent() {
    }

    private PaymentPayload paymentPayload;

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

    public PaymentSuccessEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }

    public PaymentSuccessEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public PaymentSuccessEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public PaymentSuccessEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public PaymentSuccessEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public PaymentSuccessEvent setSourceService(String sourceService) {
        this.sourceService = sourceService;
        return this;
    }

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public PaymentSuccessEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentSuccessEvent{");
        sb.append("paymentPayload=").append(paymentPayload);
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
