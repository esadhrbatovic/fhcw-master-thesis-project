package com.hrbatovic.springboot.master.user.messaging.producers;

import com.hrbatovic.springboot.master.user.messaging.model.out.UserUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserUpdatedEventProducer {

    private final KafkaTemplate<String, UserUpdatedEvent> kafkaTemplate;
    private final String topic = "user-updated";

    public UserUpdatedEventProducer(KafkaTemplate<String, UserUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserUpdatedEvent userUpdatedEvent) {
        kafkaTemplate.send(topic, userUpdatedEvent);
    }

}
