package com.hrbatovic.quarkus.master.user.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserEntityDeserializer extends ObjectMapperDeserializer<UserEntity> {
    public UserEntityDeserializer() {
        super(new TypeReference<>() {
        });
    }
}