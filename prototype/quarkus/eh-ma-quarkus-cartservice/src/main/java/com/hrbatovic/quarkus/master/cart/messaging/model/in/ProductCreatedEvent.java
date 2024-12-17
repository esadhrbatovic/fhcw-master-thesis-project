package com.hrbatovic.quarkus.master.cart.messaging.model.in;

import com.hrbatovic.quarkus.master.cart.messaging.model.in.payload.ProductPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
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
