package com.hrbatovic.quarkus.master.cart.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.UserRegisteredEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserRegisteredDeserializer extends ObjectMapperDeserializer<UserRegisteredEvent> {
    public UserRegisteredDeserializer() {
        super(new TypeReference<>() {
        });
    }
}