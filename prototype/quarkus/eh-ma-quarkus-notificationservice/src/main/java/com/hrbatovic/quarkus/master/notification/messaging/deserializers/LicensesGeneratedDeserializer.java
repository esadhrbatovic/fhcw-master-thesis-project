package com.hrbatovic.quarkus.master.notification.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.LicenseGeneratedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicensesGeneratedDeserializer extends ObjectMapperDeserializer<LicenseGeneratedEvent> {
    public LicensesGeneratedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}