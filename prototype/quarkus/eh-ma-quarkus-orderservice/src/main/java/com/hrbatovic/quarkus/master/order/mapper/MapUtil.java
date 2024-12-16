package com.hrbatovic.quarkus.master.order.mapper;

import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderItem;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.db.entities.OrderItemEntity;
import com.hrbatovic.quarkus.master.order.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.in.payload.CartPayload;
import com.hrbatovic.quarkus.master.order.messaging.model.in.payload.CartProductPayload;
import com.hrbatovic.quarkus.master.order.messaging.model.in.payload.UserPayload;
import com.hrbatovic.quarkus.master.order.messaging.model.out.payload.OrderPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract List<Order> toOrderList(List<OrderEntity> orderEntityList);

    @Mapping(target="orderId", source="id")
    @Mapping(target="items", source="orderItems")
    @Mapping(target="removeItemsItem", ignore = true)
    public abstract Order map(OrderEntity orderEntity);

    @Mapping(target="price", source="productPrice")
    public abstract OrderItem map(OrderItemEntity orderItemEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract List<OrderItemEntity> toOrderItemEntityList(List<CartProductPayload> cartProducts);

    @Mapping(target="status", constant = "OPEN")
    @Mapping(target="currency", constant = "EUR")
    @Mapping(target="totalAmount", source = "totalPrice")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target="updatedAt", ignore = true)
    @Mapping(target="orderItems", source = "cartProducts")
    @Mapping(target="paymentToken", ignore = true)
    @Mapping(target="paymentMethod", ignore = true)
    @Mapping(target="statusDetail", ignore = true)
    public abstract OrderEntity map(CartPayload cart);

    public abstract OrderPayload toOrderPayload(OrderEntity orderEntity);

}
