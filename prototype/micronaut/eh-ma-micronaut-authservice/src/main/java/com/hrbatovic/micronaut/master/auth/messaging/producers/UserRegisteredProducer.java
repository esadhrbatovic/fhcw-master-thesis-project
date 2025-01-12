package com.hrbatovic.micronaut.master.auth.messaging.producers;

import com.hrbatovic.micronaut.master.auth.messaging.model.out.UserRegisteredEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "user-registered-out")
public interface UserRegisteredProducer {

    @Topic("user-registered")
    void send(UserRegisteredEvent userRegisteredEvent);

}
