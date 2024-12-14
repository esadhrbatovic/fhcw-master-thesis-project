package com.hrbatovic.quarkus.master.order.mapper;

import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderItem;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.db.entities.OrderItemEntity;
import com.hrbatovic.quarkus.master.order.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.CartProductEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.in.UserRegisteredEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {


    public abstract List<OrderItemEntity> toOrderItemEntityList(List<CartProductEntity> cartProducts);

    public abstract List<Order> toOrderList(List<OrderEntity> orderEntityList);

    @Mapping(target="orderId", source="id")
    @Mapping(target="items", source="orderItems")
    public abstract Order map(OrderEntity orderEntity);

    @Mapping(target="price", source="productPrice")
    public abstract OrderItem map(OrderItemEntity orderItemEntity);

    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);
}
