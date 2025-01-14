package com.hrbatovic.springboot.master.user.messaging.consumers;

import com.hrbatovic.springboot.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.springboot.master.user.messaging.model.in.UserRegisteredEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(groupId = "user-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void consumeUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Consumed JSON message: " + userRegisteredEvent);
    }

    @KafkaListener(groupId = "user-group", topics = "user-credentials-updated", containerFactory = "userCredentialsUpdatedFactory")
    public void consumeUserRegisteredEvent(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Consumed JSON message: " + userCredentialsUpdatedEvent);
    }
}
