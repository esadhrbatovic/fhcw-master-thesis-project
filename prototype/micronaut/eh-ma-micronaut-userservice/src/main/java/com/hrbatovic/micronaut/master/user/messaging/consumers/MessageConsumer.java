package com.hrbatovic.micronaut.master.user.messaging.consumers;

import com.hrbatovic.micronaut.master.user.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.user.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.user.mapper.MapUtil;
import com.hrbatovic.micronaut.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.micronaut.master.user.messaging.model.in.UserRegisteredEvent;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@KafkaListener(groupId = "user-events-group")
public class MessageConsumer {

    @Inject
    UserRepository userRepository;

    @Inject
    MapUtil mapper;

    @Topic("user-registered")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("user-registered message received: " + userRegisteredEvent);
        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);

        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());

        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userRepository.save(userEntity);
    }

    @Topic("user-credentials-updated")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("user-credentials-updated message received: " + userCredentialsUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userCredentialsUpdatedEvent.getId()).orElse(null);
        if (userEntity == null) {
            return;
        }
        userEntity = mapper.update(userEntity, userCredentialsUpdatedEvent);
        userEntity.setUpdatedAt(LocalDateTime.now());
        userRepository.update(userEntity);
    }

}
