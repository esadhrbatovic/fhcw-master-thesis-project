package com.hrbatovic.springboot.master.product.messaging.producers;

import com.hrbatovic.springboot.master.product.messaging.model.out.ProductUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdatedEventProducer {

    private final KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate;
    private final String topic = "product-created";

    public ProductUpdatedEventProducer(KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ProductUpdatedEvent productDeletedEvent) {
        kafkaTemplate.send(topic, productDeletedEvent);
    }

}
