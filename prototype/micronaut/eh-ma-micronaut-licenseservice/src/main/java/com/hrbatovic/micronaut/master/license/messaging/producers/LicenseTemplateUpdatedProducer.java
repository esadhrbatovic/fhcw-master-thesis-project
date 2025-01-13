package com.hrbatovic.micronaut.master.license.messaging.producers;

import com.hrbatovic.micronaut.master.license.messaging.model.out.LicenseTemplateCreatedEvent;
import com.hrbatovic.micronaut.master.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id="license-template-updated-out")
public interface LicenseTemplateUpdatedProducer {

    @Topic("license-template-updated")
    void send(LicenseTemplateUpdatedEvent licenseTemplateUpdatedEvent);
}
