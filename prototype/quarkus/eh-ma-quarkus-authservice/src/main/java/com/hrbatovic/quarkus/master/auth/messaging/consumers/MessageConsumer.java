package com.hrbatovic.quarkus.master.auth.messaging.consumers;

import com.hrbatovic.quarkus.master.auth.Hasher;
import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.quarkus.master.auth.messaging.model.UserUpdateMsgPayload;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.hrbatovic.quarkus.master.auth.mapper.Mapper;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    Hasher hasher;

    @Incoming("user-updated-in")
    public void consumeUserUpdated(UserUpdateMsgPayload userUpdateMsgPayload) {
        System.out.println("Recieved user-updated-in event");
        executor.runAsync(() -> {
            RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(userUpdateMsgPayload.getId());
            if (registrationEntity == null) {
                return;
            }

            if (registrationEntity.getCredentialsEntity() == null) {
                return;
            }

            registrationEntity.setUserEntity(Mapper.update(userUpdateMsgPayload, registrationEntity.getUserEntity()));

            registrationEntity.getCredentialsEntity().setEmail(userUpdateMsgPayload.getEmail());

            if(userUpdateMsgPayload.getPassword() != null){
                registrationEntity.getCredentialsEntity().setPassword(hasher.hash(userUpdateMsgPayload.getPassword()));
            }

            registrationEntity.persistOrUpdate();
        });
    }

    @Incoming("user-deleted-in")
    public void consumeUserDeleted(UUID id) {
        executor.runAsync(() -> {
            RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(id);
            if (registrationEntity == null) {
                return;
            }

            registrationEntity.delete();
        });

    }
}
