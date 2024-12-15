package com.hrbatovic.master.quarkus.license.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.master.quarkus.license.messaging.model.in.PaymentSuccessEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentSuccessDeserializer extends ObjectMapperDeserializer<PaymentSuccessEvent> {
    public PaymentSuccessDeserializer() {
        super(new TypeReference<>() {
        });
    }
}