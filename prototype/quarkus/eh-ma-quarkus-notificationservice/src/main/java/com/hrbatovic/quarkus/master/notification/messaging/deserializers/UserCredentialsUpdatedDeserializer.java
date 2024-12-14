package com.hrbatovic.quarkus.master.notification.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserCredentialsUpdatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserCredentialsUpdatedDeserializer extends ObjectMapperDeserializer<UserCredentialsUpdatedEvent> {
    public UserCredentialsUpdatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}