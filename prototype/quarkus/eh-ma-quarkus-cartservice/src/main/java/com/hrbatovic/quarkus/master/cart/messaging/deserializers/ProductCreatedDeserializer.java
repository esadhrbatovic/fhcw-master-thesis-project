package com.hrbatovic.quarkus.master.cart.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.ProductCreatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ProductCreatedDeserializer extends ObjectMapperDeserializer<ProductCreatedEvent> {
    public ProductCreatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}