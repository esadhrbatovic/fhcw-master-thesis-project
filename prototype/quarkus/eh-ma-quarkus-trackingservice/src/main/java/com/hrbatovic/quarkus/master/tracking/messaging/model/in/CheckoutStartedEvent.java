package com.hrbatovic.quarkus.master.tracking.messaging.model.in;

import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.CartPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class CheckoutStartedEvent implements Serializable {

    private UUID paymentToken;

    private String paymentMethodSelector;

    private CartPayload cart;

    private LocalDateTime timestamp;

    public CheckoutStartedEvent() {
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public CheckoutStartedEvent setPaymentToken(UUID paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    public String getPaymentMethodSelector() {
        return paymentMethodSelector;
    }

    public CheckoutStartedEvent setPaymentMethodSelector(String paymentMethodSelector) {
        this.paymentMethodSelector = paymentMethodSelector;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("paymentToken", paymentToken)
                .append("paymentMethodSelector", paymentMethodSelector)
                .append("cart", cart)
                .append("timestamp", timestamp)
                .toString();
    }
}
