package com.hrbatovic.micronaut.master.license.messaging.producers;

import com.hrbatovic.micronaut.master.license.messaging.model.out.LicenseTemplateCreatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id="license-template-created-out")
public interface LicenseTemplateCreatedProducer {

    @Topic("license-template-created")
    void send(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent);

}
