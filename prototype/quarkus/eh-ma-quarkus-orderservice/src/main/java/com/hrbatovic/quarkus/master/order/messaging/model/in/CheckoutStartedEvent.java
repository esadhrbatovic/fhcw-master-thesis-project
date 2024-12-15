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

    private String paymentMethodSelector;

    private LocalDateTime timestamp;

    public CartPayload getCart() {
        return cart;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getPaymentToken() {
        return paymentToken;
    }

    public String getPaymentMethodSelector() {
        return paymentMethodSelector;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cart", cart)
                .append("paymentToken", paymentToken)
                .append("paymentMethodSelector", paymentMethodSelector)
                .append("timestamp", timestamp)
                .toString();
    }
}
