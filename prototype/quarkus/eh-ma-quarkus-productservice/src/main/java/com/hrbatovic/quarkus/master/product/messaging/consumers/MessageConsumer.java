package com.hrbatovic.quarkus.master.product.messaging.consumers;

import com.hrbatovic.quarkus.master.product.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.product.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.product.mapper.MapUtil;
import com.hrbatovic.quarkus.master.product.messaging.model.in.LicenseTemplateCreatedEvent;
import com.hrbatovic.quarkus.master.product.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.quarkus.master.product.messaging.model.in.UserUpdatedEvent;
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

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        executor.runAsync(()->{

            if(UserEntity.findById(userRegisteredEvent.getUserPayload().getId()) != null){
                return;
            }

            UserEntity userEntity = mapper.map(userRegisteredEvent.getUserPayload());

            userEntity.persist();
        });
    }

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);

        executor.runAsync(()->{
            UserEntity userEntity = UserEntity.findById(userUpdatedEvent.getUserPayload().getId());
            if(userEntity == null){
                return;
            }

            mapper.update(userEntity, userUpdatedEvent.getUserPayload());
            userEntity.update();
        });
    }

    @Incoming("user-deleted-in")
    public void consumeUserDeleted(UUID id) {
        System.out.println("Recieved user-deleted-in event: " + id);
        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(id);
            if (userEntity == null) {
                return;
            }

            userEntity.delete();
        });
    }

    @Incoming("license-template-created-in")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent) {
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);

        executor.runAsync(()->{

            ProductEntity productEntity = ProductEntity.findById(licenseTemplateCreatedEvent.getLicenseTemplate().getProductId());
            if(productEntity == null){
                return;
            }

            productEntity.setLicenseAvailable(true);
            productEntity.update();
        });
    }

    @Incoming("license-template-deleted-in")
    public void onLicenseTemplateDeleted(UUID productId) {
        System.out.println("Recieved license-template-deleted-in event: " + productId);

        executor.runAsync(()->{

            ProductEntity productEntity = ProductEntity.findById(productId);
            if(productEntity == null){
                return;
            }

            productEntity.setLicenseAvailable(false);
            productEntity.update();
        });
    }

}
