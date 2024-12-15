package com.hrbatovic.quarkus.master.order.messaging.model.out.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemPayload implements Serializable {

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal productPrice;

    private BigDecimal totalPrice;

    public UUID getProductId() {
        return productId;
    }

    public OrderItemPayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public OrderItemPayload setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItemPayload setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public OrderItemPayload setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderItemPayload setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @Override
    public String toString() {
        return "OrderItemPayload{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

