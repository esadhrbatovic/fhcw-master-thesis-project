package com.hrbatovic.quarkus.master.order.mapper;

import com.hrbatovic.quarkus.master.order.db.entities.OrderItemEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.CartProductEntity;

import java.util.List;

public abstract class Mapper {


    public static List<OrderItemEntity> map(List<CartProductEntity> cartProducts) {
    }
}
