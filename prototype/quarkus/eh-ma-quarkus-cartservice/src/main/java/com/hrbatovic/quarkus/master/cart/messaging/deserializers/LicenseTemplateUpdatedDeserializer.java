package com.hrbatovic.quarkus.master.cart.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.LicenseTemplateUpdatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicenseTemplateUpdatedDeserializer extends ObjectMapperDeserializer<LicenseTemplateUpdatedEvent> {
    public LicenseTemplateUpdatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}