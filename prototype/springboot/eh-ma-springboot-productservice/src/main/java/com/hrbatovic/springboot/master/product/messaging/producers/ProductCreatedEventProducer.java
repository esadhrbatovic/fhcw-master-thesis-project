package com.hrbatovic.springboot.master.product.messaging.producers;

import com.hrbatovic.springboot.master.product.messaging.model.out.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCreatedEventProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final String topic = "product-created";

    public ProductCreatedEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ProductCreatedEvent productCreatedEvent) {
        kafkaTemplate.send(topic, productCreatedEvent);
    }

}
