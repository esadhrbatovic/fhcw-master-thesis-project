package com.hrbatovic.springboot.master.auth.messaging.consumers;

import com.hrbatovic.springboot.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.springboot.master.auth.db.repositories.RegistrationRepository;
import com.hrbatovic.springboot.master.auth.mapper.MapUtil;
import com.hrbatovic.springboot.master.auth.messaging.model.in.UserDeletedEvent;
import com.hrbatovic.springboot.master.auth.messaging.model.in.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    MapUtil mapper;


    @KafkaListener(groupId = "auth-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(userUpdatedEvent.getUserPayload().getId()).orElse(null);

        if(registrationEntity == null){
            return;
        }

        mapper.update(userUpdatedEvent.getUserPayload(), registrationEntity.getUserEntity());
        registrationRepository.save(registrationEntity);
    }


    @KafkaListener(groupId = "auth-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDelted(UserDeletedEvent userDeletedEvent) {
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);

        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(userDeletedEvent.getId()).orElse(null);
        if(registrationEntity == null){
            return;
        }

        registrationRepository.delete(registrationEntity);
    }


}
