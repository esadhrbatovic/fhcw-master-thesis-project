package com.hrbatovic.master.quarkus.payment.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.UserRegisteredEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserRegisteredDeserializer extends ObjectMapperDeserializer<UserRegisteredEvent> {
    public UserRegisteredDeserializer() {
        super(new TypeReference<>() {
        });
    }
}