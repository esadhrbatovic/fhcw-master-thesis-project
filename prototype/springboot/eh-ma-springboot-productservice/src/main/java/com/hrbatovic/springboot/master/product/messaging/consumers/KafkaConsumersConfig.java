package com.hrbatovic.springboot.master.product.messaging.consumers;

import com.hrbatovic.springboot.master.product.messaging.model.in.*;
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
        System.out.println("generateFactory");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"); //TODO: configurable
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props, keyDeserializer, valueDeserializer);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateCreatedEvent> licenseTemplateCreatedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(LicenseTemplateCreatedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateUpdatedEvent> licenseTemplateUpdatedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateUpdatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(LicenseTemplateUpdatedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateDeletedEvent> licenseTemplateDeletedFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LicenseTemplateDeletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = generateFactory(new StringDeserializer(), new JsonDeserializer<>(LicenseTemplateDeletedEvent.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> userRegisteredFactory() {

        System.out.println("userRegisteredFactory");
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
