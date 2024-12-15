package com.hrbatovic.quarkus.master.order.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.order.messaging.model.in.PaymentFailEvent;
import com.hrbatovic.quarkus.master.order.messaging.model.in.PaymentSuccessEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentFailDeserializer extends ObjectMapperDeserializer<PaymentFailEvent> {
    public PaymentFailDeserializer() {
        super(new TypeReference<>() {
        });
    }
}