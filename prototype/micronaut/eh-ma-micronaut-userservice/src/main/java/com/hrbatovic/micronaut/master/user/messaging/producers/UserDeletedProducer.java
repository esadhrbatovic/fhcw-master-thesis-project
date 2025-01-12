package com.hrbatovic.micronaut.master.user.messaging.producers;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

import java.util.UUID;

@KafkaClient(id = "user-deleted-out")
public interface UserDeletedProducer {


    @Topic("user-deleted")
    void send(UUID id);
}
