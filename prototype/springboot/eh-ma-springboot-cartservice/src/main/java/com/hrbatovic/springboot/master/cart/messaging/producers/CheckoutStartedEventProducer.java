package com.hrbatovic.springboot.master.cart.messaging.producers;

import com.hrbatovic.springboot.master.cart.messaging.model.out.CheckoutStartedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CheckoutStartedEventProducer {

    private final KafkaTemplate<String, CheckoutStartedEvent> kafkaTemplate;
    private final String topic = "checkout-started";

    public CheckoutStartedEventProducer(KafkaTemplate<String, CheckoutStartedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CheckoutStartedEvent checkoutStartedEvent) {
        kafkaTemplate.send(topic, checkoutStartedEvent);
    }
}
