package com.hrbat.micronaut.master.authservice;

import com.hrbat.micronaut.master.authservice.messaging.model.out.UserRegisteredEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "user-registered-out")
public interface UserRegisteredProducer {

    @Topic("user-registered")
    void sendMessage(UserRegisteredEvent userRegisteredEvent);

}
