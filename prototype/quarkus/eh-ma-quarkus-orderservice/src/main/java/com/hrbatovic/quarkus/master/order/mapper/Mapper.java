package com.hrbatovic.quarkus.master.order.mapper;

import com.hrbatovic.master.quarkus.order.model.OrderItem;
import com.hrbatovic.quarkus.master.order.db.entities.OrderItemEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.CartProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Mapper {

    public static List<OrderItemEntity> map(List<CartProductEntity> cartProducts) {
        if (cartProducts == null || cartProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Map each CartProductEntity to an OrderItemEntity
        return cartProducts.stream().map(cartProduct -> {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setProductId(cartProduct.getProductId());
            orderItem.setProductTitle(cartProduct.getProductTitle());
            orderItem.setQuantity(cartProduct.getQuantity());
            orderItem.setProductPrice(cartProduct.getProductPrice());
            orderItem.setTotalPrice(cartProduct.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());
    }

    public static List<OrderItem> mapToOrderItems(List<OrderItemEntity> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return new ArrayList<>();
        }

        return orderItems.stream().map(orderItemEntity -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemEntity.getProductId());
            orderItem.setProductTitle(orderItemEntity.getProductTitle());
            orderItem.setQuantity(orderItemEntity.getQuantity());
            orderItem.setPrice(orderItemEntity.getProductPrice() != null ? orderItemEntity.getProductPrice().floatValue() : null);
            orderItem.setTotalPrice(orderItemEntity.getTotalPrice() != null ? orderItemEntity.getTotalPrice().floatValue() : null);
            return orderItem;
        }).collect(Collectors.toList());
    }
}
