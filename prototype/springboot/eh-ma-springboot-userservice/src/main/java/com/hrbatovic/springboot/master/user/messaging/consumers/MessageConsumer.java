package com.hrbatovic.springboot.master.user.messaging.consumers;

import com.hrbatovic.springboot.master.user.db.entities.UserEntity;
import com.hrbatovic.springboot.master.user.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.user.mapper.MapUtil;
import com.hrbatovic.springboot.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.springboot.master.user.messaging.model.in.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapUtil mapper;

    @KafkaListener(groupId = "user-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void consumeUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {

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

    @KafkaListener(groupId = "user-group", topics = "user-credentials-updated", containerFactory = "userCredentialsUpdatedFactory")
    public void consumeUserRegisteredEvent(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("user-credentials-updated message received: " + userCredentialsUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userCredentialsUpdatedEvent.getId()).orElse(null);
        if (userEntity == null) {
            return;
        }
        userEntity = mapper.update(userEntity, userCredentialsUpdatedEvent);
        userEntity.setUpdatedAt(LocalDateTime.now());
        userRepository.save(userEntity);
    }
}
