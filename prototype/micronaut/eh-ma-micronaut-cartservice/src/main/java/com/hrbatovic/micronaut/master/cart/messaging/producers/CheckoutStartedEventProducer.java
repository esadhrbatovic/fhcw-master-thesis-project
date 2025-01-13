package com.hrbatovic.micronaut.master.cart.messaging.producers;

import com.hrbatovic.micronaut.master.cart.messaging.model.out.CheckoutStartedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "checkout-started-out")
public interface CheckoutStartedEventProducer {

    @Topic("checkout-started")
    void send(CheckoutStartedEvent checkoutStartedEvent);

}
