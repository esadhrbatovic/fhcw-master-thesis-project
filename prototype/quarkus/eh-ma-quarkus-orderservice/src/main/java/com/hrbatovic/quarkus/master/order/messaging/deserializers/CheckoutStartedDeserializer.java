package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.in.CheckoutStartedEvent;
import com.hrbatovic.quarkus.master.order.messaging.model.in.UserUpdatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CheckoutStartedDeserializer extends ObjectMapperDeserializer<CheckoutStartedEvent> {
    public CheckoutStartedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}