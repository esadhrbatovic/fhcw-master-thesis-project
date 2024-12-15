package com.hrbatovic.quarkus.master.product.messaging.model.out;

import com.hrbatovic.quarkus.master.product.messaging.model.out.payload.ProductPayload;

import java.io.Serializable;

public class ProductCreatedEvent implements Serializable {
    ProductPayload product;

    public ProductCreatedEvent(ProductPayload product) {
        this.product = product;
    }

    public ProductPayload getProduct() {
        return product;
    }

    public void setProduct(ProductPayload product) {
        this.product = product;
    }
}
