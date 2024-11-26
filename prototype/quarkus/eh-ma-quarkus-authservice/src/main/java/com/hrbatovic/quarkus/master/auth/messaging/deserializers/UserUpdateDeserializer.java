package com.hrbatovic.quarkus.master.auth.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.auth.messaging.model.UserUpdateMsgPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserUpdateDeserializer extends ObjectMapperDeserializer<UserUpdateMsgPayload> {
    public UserUpdateDeserializer() {
        super(new TypeReference<>() {
        });
    }
}