package com.hrbatovic.quarkus.master.order.messaging.model.in;

import com.hrbatovic.quarkus.master.order.messaging.model.in.payload.CartPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public CartPayload getCart() {
        return cart;
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public String getPaymentMethod() {
        return paymentMethod;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cart", cart)
                .append("paymentToken", paymentToken)
                .append("paymentMethod", paymentMethod)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .toString();
    }
}
