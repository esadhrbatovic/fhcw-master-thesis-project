package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.LicenseTemplateCreatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicenseTemplateCreatedDeserializer extends ObjectMapperDeserializer<LicenseTemplateCreatedEvent> {
    public LicenseTemplateCreatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}