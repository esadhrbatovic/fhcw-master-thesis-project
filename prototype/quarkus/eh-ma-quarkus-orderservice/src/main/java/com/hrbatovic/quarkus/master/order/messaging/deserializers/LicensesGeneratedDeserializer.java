package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.LicensesGeneratedEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicensesGeneratedDeserializer extends ObjectMapperDeserializer<LicensesGeneratedEventPayload> {
    public LicensesGeneratedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}