package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.PaymentSuccessEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentSuccessDeserializer extends ObjectMapperDeserializer<PaymentSuccessEvent> {
    public PaymentSuccessDeserializer() {
        super(new TypeReference<>() {
        });
    }
}