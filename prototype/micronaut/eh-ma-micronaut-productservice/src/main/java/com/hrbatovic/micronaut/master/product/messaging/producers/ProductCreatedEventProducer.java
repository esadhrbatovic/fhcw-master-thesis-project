package com.hrbatovic.micronaut.master.product.messaging.producers;

import com.hrbatovic.micronaut.master.product.messaging.model.out.ProductCreatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "product-created-out")
public interface ProductCreatedEventProducer {

    @Topic("product-created")
    public void send(ProductCreatedEvent productCreatedEvent);

}
