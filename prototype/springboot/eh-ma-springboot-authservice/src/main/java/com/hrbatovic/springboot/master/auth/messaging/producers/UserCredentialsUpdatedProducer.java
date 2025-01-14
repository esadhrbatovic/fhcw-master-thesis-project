package com.hrbatovic.springboot.master.auth.messaging.producers;

import com.hrbatovic.springboot.master.auth.messaging.model.out.UserCredentialsUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsUpdatedProducer {


    private final KafkaTemplate<String, UserCredentialsUpdatedEvent> kafkaTemplate;
    private final String topic = "user-credentials-updated";

    public UserCredentialsUpdatedProducer(KafkaTemplate<String, UserCredentialsUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        kafkaTemplate.send(topic, userCredentialsUpdatedEvent);
    }
}
