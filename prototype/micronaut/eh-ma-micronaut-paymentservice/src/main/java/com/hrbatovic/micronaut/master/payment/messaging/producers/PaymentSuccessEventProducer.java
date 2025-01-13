package com.hrbatovic.micronaut.master.payment.messaging.producers;

import com.hrbatovic.micronaut.master.payment.messaging.model.out.PaymentSuccessEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "payment-success-out")
public interface PaymentSuccessEventProducer {

    @Topic("payment-success")
    public void send(PaymentSuccessEvent paymentSuccessEvent);
}
