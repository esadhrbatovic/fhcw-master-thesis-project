package com.hrbatovic.springboot.master.license.messaging.producers;

import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LicenseTemplateCreatedProducer {
    private final KafkaTemplate<String, LicenseTemplateCreatedEvent> kafkaTemplate;
    private final String topic = "license-template-created";

    public LicenseTemplateCreatedProducer(KafkaTemplate<String, LicenseTemplateCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(LicenseTemplateCreatedEvent licensesGeneratedEvent) {
        kafkaTemplate.send(topic, licensesGeneratedEvent);
    }
}
