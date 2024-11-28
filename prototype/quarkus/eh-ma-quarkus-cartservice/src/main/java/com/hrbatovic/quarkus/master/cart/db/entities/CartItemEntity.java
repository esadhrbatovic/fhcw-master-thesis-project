package com.hrbatovic.quarkus.master.cart.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private UUID productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal productPrice;

    private BigDecimal totalPrice;




    public CartItemEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItemEntity{" +
                "id=" + id +
                ", productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
