package com.hrbatovic.master.quarkus.license.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.master.quarkus.license.messaging.model.PaymentSuccessEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentSuccessDeserializer extends ObjectMapperDeserializer<PaymentSuccessEventPayload> {
    public PaymentSuccessDeserializer() {
        super(new TypeReference<>() {
        });
    }
}