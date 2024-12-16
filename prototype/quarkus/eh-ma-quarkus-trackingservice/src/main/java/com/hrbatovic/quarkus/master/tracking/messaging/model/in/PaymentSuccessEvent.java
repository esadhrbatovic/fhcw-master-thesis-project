package com.hrbatovic.quarkus.master.tracking.messaging.model.in;

import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.PaymentPayload;
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

    private String sourceService;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("paymentPayload", paymentPayload)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .toString();
    }
}
