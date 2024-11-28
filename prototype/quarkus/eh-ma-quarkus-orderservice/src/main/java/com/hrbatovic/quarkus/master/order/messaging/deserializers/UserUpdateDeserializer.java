package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.UserUpdatedMsgPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserUpdateDeserializer extends ObjectMapperDeserializer<UserUpdatedMsgPayload> {
    public UserUpdateDeserializer() {
        super(new TypeReference<>() {
        });
    }
}