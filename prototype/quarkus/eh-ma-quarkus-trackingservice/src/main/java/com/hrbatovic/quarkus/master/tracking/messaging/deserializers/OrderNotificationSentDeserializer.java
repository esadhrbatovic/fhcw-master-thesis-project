package com.hrbatovic.quarkus.master.tracking.messaging.deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.OrderNotificationSentEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderNotificationSentDeserializer extends ObjectMapperDeserializer<OrderNotificationSentEvent> {
    public OrderNotificationSentDeserializer() {
        super(new TypeReference<>() {
        });
    }
}