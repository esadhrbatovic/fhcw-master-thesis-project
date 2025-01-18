package com.hrbatovic.springboot.master.order.messaging.consumers;

import com.hrbatovic.springboot.master.order.messaging.model.in.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumersConfig {

    private <K, V> ConsumerFactory<K, V> generateFactory(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"); //TODO: configurable
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CheckoutStartedEvent> checkoutStartedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CheckoutStartedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(CheckoutStartedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LicensesGeneratedEvent> licensesGeneratedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LicensesGeneratedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(LicensesGeneratedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderNotificationSentEvent> orderNotificationSentFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderNotificationSentEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(OrderNotificationSentEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailEvent> paymentFailFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentFailEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(PaymentFailEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> paymentSuccessFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(PaymentSuccessEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> userRegisteredFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(UserRegisteredEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserUpdatedEvent> userUpdatedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserUpdatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(UserUpdatedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDeletedEvent> userDeletedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserDeletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(UserDeletedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
