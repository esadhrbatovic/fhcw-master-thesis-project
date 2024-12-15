package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.OrderCreatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderCreatedDeserializer extends ObjectMapperDeserializer<OrderCreatedEvent> {
    public OrderCreatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}