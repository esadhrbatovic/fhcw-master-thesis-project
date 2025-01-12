package com.hrbatovic.micronaut.master.auth.messaging.producers;

import com.hrbatovic.micronaut.master.auth.messaging.model.out.UserCredentialsUpdatedEvent;
import com.hrbatovic.micronaut.master.auth.messaging.model.out.UserRegisteredEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "user-credentials-updated-out")
public interface UserCredentialsUpdatedProducer {

    @Topic("user-credentials-updated")
    void send(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);
}