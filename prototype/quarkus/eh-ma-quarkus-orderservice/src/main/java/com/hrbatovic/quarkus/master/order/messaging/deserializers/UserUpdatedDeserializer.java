package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.in.UserUpdatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserUpdatedDeserializer extends ObjectMapperDeserializer<UserUpdatedEvent> {
    public UserUpdatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}