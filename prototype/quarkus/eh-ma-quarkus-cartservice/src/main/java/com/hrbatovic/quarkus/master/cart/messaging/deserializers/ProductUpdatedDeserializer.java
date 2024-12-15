package com.hrbatovic.quarkus.master.cart.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.ProductUpdatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ProductUpdatedDeserializer extends ObjectMapperDeserializer<ProductUpdatedEvent> {
    public ProductUpdatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}