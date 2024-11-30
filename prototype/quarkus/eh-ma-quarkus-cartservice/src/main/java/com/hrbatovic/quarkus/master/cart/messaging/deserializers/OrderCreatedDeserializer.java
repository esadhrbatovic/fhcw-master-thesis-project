package com.hrbatovic.quarkus.master.cart.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.cart.messaging.model.OrderCreatedEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderCreatedDeserializer extends ObjectMapperDeserializer<OrderCreatedEventPayload> {
    public OrderCreatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}