package com.hrbatovic.quarkus.master.cart.mapper;

import com.hrbatovic.master.quarkus.cart.model.CartItem;
import com.hrbatovic.quarkus.master.cart.db.entities.CartItemEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mapper {


    public static CartItem map(CartItemEntity cartItemEntity) {
        CartItem cartItem = new CartItem();

        cartItem.setItemId(cartItemEntity.getId());
        cartItem.setPrice(cartItemEntity.getProductPrice().floatValue());
        cartItem.setTotalPrice(cartItemEntity.getTotalPrice().floatValue());
        cartItem.setProductTitle(cartItemEntity.getProductTitle());
        cartItem.setQuantity(cartItemEntity.getQuantity());
        cartItem.setProductId(cartItemEntity.getProductId());

        return cartItem;

    }
}
