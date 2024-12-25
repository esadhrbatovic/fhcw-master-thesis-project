package com.hrbatovic.quarkus.master.tracking.messaging.model.in;

import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.PaymentPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@RegisterForReflection
public class PaymentSuccessEvent implements Serializable {

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
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("paymentPayload", paymentPayload)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
