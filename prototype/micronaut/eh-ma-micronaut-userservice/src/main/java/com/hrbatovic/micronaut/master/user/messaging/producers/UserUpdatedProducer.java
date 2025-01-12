package com.hrbatovic.micronaut.master.user.messaging.producers;

import com.hrbatovic.micronaut.master.user.messaging.model.out.UserUpdatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "user-updated-out")
public interface UserUpdatedProducer {

    @Topic("user-updated")
    void send(UserUpdatedEvent userUpdatedEvent);
}
