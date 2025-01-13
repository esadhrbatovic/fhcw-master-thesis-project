package com.hrbatovic.micronaut.master.payment.messaging.producers;

import com.hrbatovic.micronaut.master.payment.messaging.model.out.PaymentFailEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "payment-fail-out")
public interface PaymentFailEventProducer {

    @Topic("payment-fail")
    public void send(PaymentFailEvent paymentFailEvent);
}
