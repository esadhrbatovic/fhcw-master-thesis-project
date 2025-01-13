package com.hrbatovic.micronaut.master.license.messaging.producers;

import com.hrbatovic.micronaut.master.license.messaging.model.out.LicensesGeneratedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id="licenses-generated-out")
public interface LicensesGeneratedEventProducer {

    @Topic("licenses-generated")
    void send(LicensesGeneratedEvent licensesGeneratedEvent);
}
