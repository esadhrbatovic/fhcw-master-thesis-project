package com.hrbatovic.quarkus.master.cart.messaging.model.in;

import com.hrbatovic.quarkus.master.cart.messaging.model.in.payload.ProductPayload;

import java.io.Serializable;

public class ProductUpdatedEvent implements Serializable {
    private ProductPayload product;

    public ProductUpdatedEvent() {
    }

    public ProductPayload getProduct() {
        return product;
    }

    public void setProduct(ProductPayload product) {
        this.product = product;
    }
}
