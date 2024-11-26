package com.hrbatovic.quarkus.master.user.messaging.consumer;

import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Incoming("user-registered-in")
    public void consumeUser(UserEntity userEntity) {
        System.out.println("Recieved user-registered-in event");

        executor.runAsync(() -> {
            if (UserEntity.findById(userEntity.id) != null) {
                return;
            }
            userEntity.setCreatedAt(LocalDateTime.now());
            userEntity.setUpdatedAt(LocalDateTime.now());
            userEntity.persistOrUpdate();
        });
    }

}