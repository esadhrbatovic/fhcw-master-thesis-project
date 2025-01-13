package com.hrbatovic.micronaut.master.license.messaging.producers;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

import java.util.UUID;


@KafkaClient(id="license-template-deleted-out")
public interface LicenseTemplateDeletedProducer {

    @Topic("license-template-deleted")
    void send(UUID id);
}
