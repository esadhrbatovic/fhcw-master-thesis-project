package com.hrbatovic.springboot.master.user.messaging.producers;

import com.hrbatovic.springboot.master.user.messaging.model.out.UserDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDeletedEventProducer {

    private final KafkaTemplate<String, UserDeletedEvent> kafkaTemplate;
    private final String topic = "user-deleted";

    public UserDeletedEventProducer(KafkaTemplate<String, UserDeletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserDeletedEvent userDeletedEvent) {
        kafkaTemplate.send(topic, userDeletedEvent);
    }

}
