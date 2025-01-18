package com.hrbatovic.springboot.master.order.messaging.producers;

import com.hrbatovic.springboot.master.order.messaging.model.out.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final String topic = "order-created";

    public OrderCreatedEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send(topic, orderCreatedEvent);
    }

}
