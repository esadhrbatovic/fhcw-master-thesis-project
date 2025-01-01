package com.hrbat.micronaut.master.userservice;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(groupId = "user-events-group")
public class MessageConsumer {

    @Topic("user-credentials-updated")
    public void receiveUserCredentialsUpdated(String message) {
        System.out.println("user-credentials-updated message received: " + message);
    }

    @Topic("user-registered")
    public void receiveUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("user-registered message received: " + userRegisteredEvent);
    }

}
