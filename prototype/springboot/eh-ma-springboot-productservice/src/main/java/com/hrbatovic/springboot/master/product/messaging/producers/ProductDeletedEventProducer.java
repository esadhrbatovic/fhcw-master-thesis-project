package com.hrbatovic.springboot.master.product.messaging.producers;

import com.hrbatovic.springboot.master.product.messaging.model.out.ProductDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductDeletedEventProducer {

    private final KafkaTemplate<String, ProductDeletedEvent> kafkaTemplate;
    private final String topic = "product-created";

    public ProductDeletedEventProducer(KafkaTemplate<String, ProductDeletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ProductDeletedEvent productDeletedEvent) {
        kafkaTemplate.send(topic, productDeletedEvent);
    }

}
