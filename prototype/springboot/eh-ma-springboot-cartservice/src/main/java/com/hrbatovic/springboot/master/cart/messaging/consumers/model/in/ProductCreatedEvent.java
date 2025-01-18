package com.hrbatovic.springboot.master.cart.messaging.consumers.model.in;

import com.hrbatovic.springboot.master.cart.messaging.consumers.model.in.payload.ProductPayload;

import java.io.Serializable;

public class ProductCreatedEvent implements Serializable {
    private ProductPayload product;

    public ProductCreatedEvent() {
    }

    public ProductPayload getProduct() {
        return product;
    }

    public void setProduct(ProductPayload product) {
        this.product = product;
    }
}
