package com.hrbatovic.springboot.master.payment.messaging.producers;

import com.hrbatovic.springboot.master.payment.messaging.model.out.PaymentSuccessEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentSuccessEventProducer {

    private final KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate;
    private final String topic = "payment-success";

    public PaymentSuccessEventProducer(KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(PaymentSuccessEvent paymentSuccessEvent) {
        kafkaTemplate.send(topic, paymentSuccessEvent);
    }

}
