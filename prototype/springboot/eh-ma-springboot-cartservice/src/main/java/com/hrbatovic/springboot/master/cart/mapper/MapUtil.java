package com.hrbatovic.springboot.master.cart.mapper;

import com.hrbatovic.master.springboot.cart.model.CartProduct;
import com.hrbatovic.master.springboot.cart.model.CartResponse;
import com.hrbatovic.springboot.master.cart.db.entities.CartEntity;
import com.hrbatovic.springboot.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.springboot.master.cart.db.entities.ProductEntity;
import com.hrbatovic.springboot.master.cart.db.entities.UserEntity;
import com.hrbatovic.springboot.master.cart.messaging.model.in.payload.ProductPayload;
import com.hrbatovic.springboot.master.cart.messaging.model.in.payload.UserPayload;
import com.hrbatovic.springboot.master.cart.messaging.model.out.payload.CartPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class MapUtil {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "productTitle", source = "productTitle")
    @Mapping(target = "price", source = "productPrice")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "totalPrice", source = "totalPrice")
    public abstract CartProduct map(CartProductEntity cartProductEntity);

    @Mapping(target = "productId", source="productEntity.id")
    @Mapping(target = "quantity", source="quantity")
    @Mapping(target = "totalPrice", source="totalPrice")
    @Mapping(target = "productTitle", source="productEntity.title")
    @Mapping(target = "productPrice", source="productEntity.price")
    public abstract CartProductEntity map(ProductEntity productEntity, Integer quantity, BigDecimal totalPrice);

    @Mapping(target = "products", source="cartProducts")
    @Mapping(target = "totalProducts", source="totalItems")
    public abstract CartResponse map(CartEntity cartEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    public abstract ProductEntity map(ProductPayload product);

    public abstract CartPayload toCartPayload(CartEntity cartEntity);
}
