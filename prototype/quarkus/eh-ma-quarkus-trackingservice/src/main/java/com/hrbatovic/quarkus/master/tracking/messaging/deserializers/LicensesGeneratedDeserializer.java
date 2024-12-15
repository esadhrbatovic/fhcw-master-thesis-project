package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.LicensesGeneratedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicensesGeneratedDeserializer extends ObjectMapperDeserializer<LicensesGeneratedEvent> {
    public LicensesGeneratedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}