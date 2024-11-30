package com.hrbatovic.quarkus.master.cart.messaging.model;

import java.io.Serializable;

public class OrderCreatedEventPayload implements Serializable {

    private OrderEntity orderEntity;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}