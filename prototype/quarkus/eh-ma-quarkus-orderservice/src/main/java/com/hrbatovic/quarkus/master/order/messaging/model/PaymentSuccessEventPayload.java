package com.hrbatovic.quarkus.master.order.messaging.model;

import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;

import java.io.Serializable;

public class PaymentSuccessEventPayload implements Serializable {

    private OrderEntity orderEntity;

    public PaymentSuccessEventPayload() {
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Override
    public String toString() {
        return "PaymentSuccessEventPayload{" +
                "orderEntity=" + orderEntity +
                '}';
    }
}