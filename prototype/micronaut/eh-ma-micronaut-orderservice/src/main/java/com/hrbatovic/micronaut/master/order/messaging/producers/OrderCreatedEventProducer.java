package com.hrbatovic.micronaut.master.order.messaging.producers;

import com.hrbatovic.micronaut.master.order.messaging.model.out.OrderCreatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "order-created-out")
public interface OrderCreatedEventProducer {

    @Topic("order-created")
    void send(OrderCreatedEvent orderCreatedEvent);
}
