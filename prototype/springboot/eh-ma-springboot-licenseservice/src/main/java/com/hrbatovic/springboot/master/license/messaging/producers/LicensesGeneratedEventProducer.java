package com.hrbatovic.springboot.master.license.messaging.producers;

import com.hrbatovic.springboot.master.license.messaging.model.out.LicensesGeneratedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LicensesGeneratedEventProducer {

    private final KafkaTemplate<String, LicensesGeneratedEvent> kafkaTemplate;
    private final String topic = "licenses-generated";

    public LicensesGeneratedEventProducer(KafkaTemplate<String, LicensesGeneratedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(LicensesGeneratedEvent licensesGeneratedEvent) {
        kafkaTemplate.send(topic, licensesGeneratedEvent);
    }
}
