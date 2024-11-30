package com.hrbatovic.quarkus.master.order.messaging.model;

import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;

import java.io.Serializable;

public class OrderCreatedEventPayload implements Serializable {

    private OrderEntity orderEntity;

    public OrderCreatedEventPayload() {
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
