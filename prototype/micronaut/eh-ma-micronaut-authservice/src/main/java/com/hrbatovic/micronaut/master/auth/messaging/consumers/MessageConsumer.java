package com.hrbatovic.micronaut.master.auth.messaging.consumers;

import com.hrbatovic.micronaut.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.micronaut.master.auth.db.repositories.RegistrationRepository;
import com.hrbatovic.micronaut.master.auth.mapper.MapUtil;
import com.hrbatovic.micronaut.master.auth.messaging.model.in.UserUpdatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.util.UUID;

@KafkaListener(groupId = "auth-events-group")
public class MessageConsumer {

    @Inject
    RegistrationRepository registrationRepository;

    @Inject
    MapUtil mapper;

    @Topic("user-updated")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(userUpdatedEvent.getUserPayload().getId()).orElse(null);

        if(registrationEntity == null){
            return;
        }

        mapper.update(userUpdatedEvent.getUserPayload(), registrationEntity.getUserEntity());
        registrationRepository.update(registrationEntity);

    }

    @Topic("user-deleted")
    public void onUserDeleted(UUID id){
        System.out.println("Recieved user-deleted-in event: " + id);

        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(id).orElse(null);
        if(registrationEntity == null){
            return;
        }

        registrationRepository.delete(registrationEntity);

    }

}
