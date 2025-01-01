package com.hrbat.micronaut.master.authservice;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "user-credentials-updated-out")
public interface UserCredentialsUpdatedProducer {

    @Topic("user-credentials-updated")
    void sendMessage(String message);
}