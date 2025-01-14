package com.hrbatovic.springboot.master.auth.messaging.producers;

import com.hrbatovic.springboot.master.auth.messaging.model.out.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredEventProducer {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
    private final String topic = "user-registered";

    public UserRegisteredEventProducer(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserRegisteredEvent userRegisteredEvent) {
        kafkaTemplate.send(topic, userRegisteredEvent);
    }
}