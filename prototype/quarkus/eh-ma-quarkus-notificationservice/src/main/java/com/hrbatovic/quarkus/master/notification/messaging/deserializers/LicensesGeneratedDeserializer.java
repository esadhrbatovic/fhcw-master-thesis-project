package com.hrbatovic.quarkus.master.notification.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.notification.messaging.model.LicensesGeneratedEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicensesGeneratedDeserializer extends ObjectMapperDeserializer<LicensesGeneratedEventPayload> {
    public LicensesGeneratedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}