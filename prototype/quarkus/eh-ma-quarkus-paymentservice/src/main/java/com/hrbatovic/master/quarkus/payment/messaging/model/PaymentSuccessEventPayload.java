package com.hrbatovic.master.quarkus.payment.messaging.model;


import java.io.Serializable;

public class PaymentSuccessEventPayload implements Serializable {

    private OrderEntity orderEntity;

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
