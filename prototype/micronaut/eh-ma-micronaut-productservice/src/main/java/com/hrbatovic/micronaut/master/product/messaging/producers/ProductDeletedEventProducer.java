package com.hrbatovic.micronaut.master.product.messaging.producers;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

import java.util.UUID;

@KafkaClient(id = "product-deleted-out")
public interface ProductDeletedEventProducer {

    @Topic("product-deleted")
    void send(UUID id);

}
