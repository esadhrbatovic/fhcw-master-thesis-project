package com.hrbatovic.springboot.master.payment.messaging.producers;

import com.hrbatovic.springboot.master.payment.messaging.model.out.PaymentFailEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentFailEventProducer {

    private final KafkaTemplate<String, PaymentFailEvent> kafkaTemplate;
    private final String topic = "payment-fail";

    public PaymentFailEventProducer(KafkaTemplate<String, PaymentFailEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PaymentFailEvent paymentFailEvent) {
        kafkaTemplate.send(topic, paymentFailEvent);
    }

}
