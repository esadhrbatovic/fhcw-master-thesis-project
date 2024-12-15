package com.hrbatovic.quarkus.master.tracking.messaging.model.in;


import com.hrbatovic.quarkus.master.tracking.messaging.model.in.payload.ProductPayload;

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
