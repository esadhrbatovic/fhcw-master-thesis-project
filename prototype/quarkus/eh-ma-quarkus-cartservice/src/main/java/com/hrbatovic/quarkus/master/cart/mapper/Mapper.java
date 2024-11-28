package com.hrbatovic.quarkus.master.cart.mapper;

import com.hrbatovic.master.quarkus.cart.model.CartProduct;
import com.hrbatovic.quarkus.master.cart.db.entities.CartProductEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mapper {


    public static CartProduct map(CartProductEntity cartProductEntity) {
        CartProduct cartItem = new CartProduct();

        cartItem.setProductId(cartProductEntity.getProductId());
        cartItem.setPrice(cartProductEntity.getProductPrice().floatValue());
        cartItem.setTotalPrice(cartProductEntity.getTotalPrice().floatValue());
        cartItem.setProductTitle(cartProductEntity.getProductTitle());
        cartItem.setQuantity(cartProductEntity.getQuantity());
        cartItem.setProductId(cartProductEntity.getProductId());

        return cartItem;

    }
}
