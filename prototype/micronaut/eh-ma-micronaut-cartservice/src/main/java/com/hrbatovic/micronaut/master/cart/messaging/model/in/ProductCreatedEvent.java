package com.hrbatovic.micronaut.master.cart.messaging.model.in;

import com.hrbatovic.micronaut.master.cart.messaging.model.in.payload.ProductPayload;
import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
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
