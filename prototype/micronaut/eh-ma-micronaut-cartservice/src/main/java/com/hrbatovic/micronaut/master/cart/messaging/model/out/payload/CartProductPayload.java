package com.hrbatovic.micronaut.master.cart.messaging.model.out.payload;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public class CartProductPayload implements Serializable {

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal productPrice;

    private BigDecimal totalPrice;

    public CartProductPayload() {
    }

    public UUID getProductId() {
        return productId;
    }

    public CartProductPayload setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public CartProductPayload setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartProductPayload setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public CartProductPayload setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public CartProductPayload setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @Override
    public String toString() {
        return "CartProductPayload{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
