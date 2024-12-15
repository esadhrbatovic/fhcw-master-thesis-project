package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.PaymentFailEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentFailDeserializer extends ObjectMapperDeserializer<PaymentFailEvent> {
    public PaymentFailDeserializer() {
        super(new TypeReference<>() {
        });
    }
}