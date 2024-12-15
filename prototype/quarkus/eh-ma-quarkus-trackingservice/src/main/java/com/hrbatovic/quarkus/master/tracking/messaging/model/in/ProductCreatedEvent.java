package com.hrbatovic.quarkus.master.tracking.messaging.model.in;


import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.ProductPayload;

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
