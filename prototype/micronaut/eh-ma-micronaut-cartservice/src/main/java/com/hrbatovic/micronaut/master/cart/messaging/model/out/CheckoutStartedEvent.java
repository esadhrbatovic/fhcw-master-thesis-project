package com.hrbatovic.micronaut.master.cart.messaging.model.out;

import com.hrbatovic.micronaut.master.cart.messaging.model.out.payload.CartPayload;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class CheckoutStartedEvent implements Serializable {

    private CartPayload cart;

    private String paymentMethod;

    private UUID paymentToken;

    //Metadata
    private LocalDateTime timestamp;

    private UUID sessionId;

    private UUID userId;

    private String userEmail;

    private final String sourceService = "cartservice";

    private UUID requestCorrelationId;

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

    public CartPayload getCart() {
        return cart;
    }

    public CheckoutStartedEvent setCart(CartPayload cart) {
        this.cart = cart;
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

    public UUID getRequestCorrelationId() {
        return requestCorrelationId;
    }

    public CheckoutStartedEvent setRequestCorrelationId(UUID requestCorrelationId) {
        this.requestCorrelationId = requestCorrelationId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cart", cart)
                .append("paymentMethod", paymentMethod)
                .append("paymentToken", paymentToken)
                .append("timestamp", timestamp)
                .append("sessionId", sessionId)
                .append("userId", userId)
                .append("userEmail", userEmail)
                .append("sourceService", sourceService)
                .append("requestCorrelationId", requestCorrelationId)
                .toString();
    }
}
