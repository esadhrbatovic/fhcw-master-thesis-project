package com.hrbatovic.quarkus.master.cart.mapper;

import com.hrbatovic.master.quarkus.cart.model.CartProduct;
import com.hrbatovic.master.quarkus.cart.model.CartResponse;
import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.CartProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "cdi")
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

}
