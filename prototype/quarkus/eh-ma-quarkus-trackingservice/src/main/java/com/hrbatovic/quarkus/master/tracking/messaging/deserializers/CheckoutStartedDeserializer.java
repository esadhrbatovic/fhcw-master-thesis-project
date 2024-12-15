package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.CheckoutStartedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CheckoutStartedDeserializer extends ObjectMapperDeserializer<CheckoutStartedEvent> {
    public CheckoutStartedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}