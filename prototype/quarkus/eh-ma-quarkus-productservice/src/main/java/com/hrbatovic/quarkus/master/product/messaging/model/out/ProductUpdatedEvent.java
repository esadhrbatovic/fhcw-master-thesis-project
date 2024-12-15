package com.hrbatovic.quarkus.master.product.messaging.model.out;

import com.hrbatovic.quarkus.master.product.messaging.model.out.payload.ProductPayload;

public class ProductUpdatedEvent {
    ProductPayload product;

    public ProductUpdatedEvent(ProductPayload product) {
        this.product = product;
    }

    public ProductPayload getProduct() {
        return product;
    }

    public void setProduct(ProductPayload product) {
        this.product = product;
    }
}
