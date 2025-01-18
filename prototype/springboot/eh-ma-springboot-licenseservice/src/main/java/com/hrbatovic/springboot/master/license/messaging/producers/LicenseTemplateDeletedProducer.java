package com.hrbatovic.springboot.master.license.messaging.producers;

import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LicenseTemplateDeletedProducer {
    private final KafkaTemplate<String, LicenseTemplateDeletedEvent> kafkaTemplate;
    private final String topic = "license-template-deleted";

    public LicenseTemplateDeletedProducer(KafkaTemplate<String, LicenseTemplateDeletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(LicenseTemplateDeletedEvent licenseTemplateDeletedEvent) {
        kafkaTemplate.send(topic, licenseTemplateDeletedEvent);
    }
}
