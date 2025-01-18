package com.hrbatovic.springboot.master.notification.messaging.producers;

import com.hrbatovic.springboot.master.notification.messaging.model.out.OrderNotificationSentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationSentEventProducer {

    private final KafkaTemplate<String, OrderNotificationSentEvent> kafkaTemplate;
    private final String topic = "order-notification-sent";

    public OrderNotificationSentEventProducer(KafkaTemplate<String, OrderNotificationSentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderNotificationSentEvent orderNotificationSentEvent) {
        kafkaTemplate.send(topic, orderNotificationSentEvent);
    }
}
