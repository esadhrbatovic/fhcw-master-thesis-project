package com.hrbatovic.quarkus.master.user.messaging.consumers;

import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.mapper.MapUtil;
import com.hrbatovic.quarkus.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.quarkus.master.user.messaging.model.in.UserRegisteredEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    MapUtil mapper;

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event");

        executor.runAsync(() -> {
            if (UserEntity.findById(userRegisteredEvent.getId()) != null) {
                return;
            }
            UserEntity userEntity = mapper.map(userRegisteredEvent);

            userEntity.setCreatedAt(LocalDateTime.now());
            userEntity.setUpdatedAt(LocalDateTime.now());
            userEntity.persist();
        });
    }

    @Incoming("user-credentials-updated-in")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event");

        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(userCredentialsUpdatedEvent.getId());
            if (userEntity == null) {
                return;
            }
            userEntity = mapper.update(userEntity, userCredentialsUpdatedEvent);

            userEntity.setUpdatedAt(LocalDateTime.now());
            userEntity.update();
        });
    }

}