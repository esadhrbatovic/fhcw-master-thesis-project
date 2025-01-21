package com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class CartProductPayload implements Serializable {


    public CartProductPayload() {
    }


    private UUID productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal productPrice;

    private BigDecimal totalPrice;

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
        final StringBuilder sb = new StringBuilder("CartProductPayload{");
        sb.append("productId=").append(productId);
        sb.append(", productTitle='").append(productTitle).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", productPrice=").append(productPrice);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append('}');
        return sb.toString();
    }
}
