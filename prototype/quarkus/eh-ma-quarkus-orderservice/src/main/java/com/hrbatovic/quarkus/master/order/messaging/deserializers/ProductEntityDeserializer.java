package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.db.entities.ProductEntity;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ProductEntityDeserializer extends ObjectMapperDeserializer<ProductEntity> {
    public ProductEntityDeserializer() {
        super(new TypeReference<>() {
        });
    }
}