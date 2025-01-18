package com.hrbatovic.springboot.master.license.messaging.producers;

import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LicenseTemplateUpdatedProducer {
    private final KafkaTemplate<String, LicenseTemplateUpdatedEvent> kafkaTemplate;
    private final String topic = "license-template-updated";

    public LicenseTemplateUpdatedProducer(KafkaTemplate<String, LicenseTemplateUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(LicenseTemplateUpdatedEvent licenseTemplateUpdatedEvent) {
        kafkaTemplate.send(topic, licenseTemplateUpdatedEvent);
    }
}
