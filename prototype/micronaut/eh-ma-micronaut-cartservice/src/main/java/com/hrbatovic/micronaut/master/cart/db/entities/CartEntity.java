package com.hrbatovic.micronaut.master.cart.db.entities;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Serdeable
@MappedEntity(value = "carts")
public class CartEntity {

    @Id
    private UUID id;

    private UUID userId;

    private List<CartProductEntity> cartProducts;

    private Integer totalItems;

    private BigDecimal totalPrice;


    public CartEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<CartProductEntity> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductEntity> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", cartProducts=" + cartProducts +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
