package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.CheckoutStartedEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CheckoutStartedDeserializer extends ObjectMapperDeserializer<CheckoutStartedEventPayload> {
    public CheckoutStartedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}