package com.hrbatovic.quarkus.master.auth.messaging.consumers;

import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.quarkus.master.auth.mapper.MapUtil;
import com.hrbatovic.quarkus.master.auth.messaging.model.in.UserUpdatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    MapUtil mapper;

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        executor.runAsync(() -> {
            RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(userUpdatedEvent.getId());
            if(registrationEntity == null){
                return;
            }

            mapper.update(userUpdatedEvent, registrationEntity.getUserEntity());

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
