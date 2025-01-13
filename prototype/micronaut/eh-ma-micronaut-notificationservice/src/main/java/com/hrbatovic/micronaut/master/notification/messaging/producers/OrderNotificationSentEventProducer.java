package com.hrbatovic.micronaut.master.notification.messaging.producers;

import com.hrbatovic.micronaut.master.notification.messaging.model.out.OrderNotificationSentEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(id = "order-notification-sent-out")
public interface OrderNotificationSentEventProducer {

    @Topic("order-notification-sent")
    void send(OrderNotificationSentEvent orderNotificationSentEvent);

}
