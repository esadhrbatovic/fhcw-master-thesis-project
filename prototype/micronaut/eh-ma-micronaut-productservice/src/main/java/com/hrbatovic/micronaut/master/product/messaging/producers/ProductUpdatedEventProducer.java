package com.hrbatovic.micronaut.master.product.messaging.producers;

import com.hrbatovic.micronaut.master.product.messaging.model.out.ProductUpdatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "product-updated-out")
public interface ProductUpdatedEventProducer {

    @Topic("product-updated")
    void send(ProductUpdatedEvent productUpdatedEvent);

}
