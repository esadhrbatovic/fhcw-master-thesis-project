package com.hrbatovic.quarkus.master.product.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.product.messaging.model.in.LicenseTemplateUpdatedEvent;
import com.hrbatovic.quarkus.master.product.messaging.model.in.UserRegisteredEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class LicenseTemplateUpdatedDeserializer extends ObjectMapperDeserializer<LicenseTemplateUpdatedEvent> {
    public LicenseTemplateUpdatedDeserializer() {
        super(new TypeReference<>() {
        });
    }
}