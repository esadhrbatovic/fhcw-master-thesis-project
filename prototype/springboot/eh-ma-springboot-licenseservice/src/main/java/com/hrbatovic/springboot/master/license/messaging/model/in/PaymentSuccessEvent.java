package com.hrbatovic.springboot.master.license.messaging.model.in;

import com.hrbatovic.springboot.master.license.messaging.model.in.payload.PaymentPayload;

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

    public PaymentSuccessEvent setPaymentPayload(PaymentPayload paymentPayload) {
        this.paymentPayload = paymentPayload;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public PaymentSuccessEvent setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public PaymentSuccessEvent setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public PaymentSuccessEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public PaymentSuccessEvent setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getSourceService() {
        return sourceService;
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

}