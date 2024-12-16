package com.hrbatovic.master.quarkus.payment.messaging.model.out;

import com.hrbatovic.master.quarkus.payment.messaging.model.out.payload.PaymentPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentSuccessEvent implements Serializable {

    private PaymentPayload paymentPayload;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "paymentservice";

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paymentPayload", paymentPayload)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .toString();
    }
}
