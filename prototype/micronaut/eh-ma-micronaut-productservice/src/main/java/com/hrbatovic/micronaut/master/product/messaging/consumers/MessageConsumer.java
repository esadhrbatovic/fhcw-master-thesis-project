package com.hrbatovic.micronaut.master.product.messaging.consumers;

import com.hrbatovic.micronaut.master.product.db.entities.ProductEntity;
import com.hrbatovic.micronaut.master.product.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.product.db.repositories.ProductRepository;
import com.hrbatovic.micronaut.master.product.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.product.mapper.MapUtil;
import com.hrbatovic.micronaut.master.product.messaging.model.in.LicenseTemplateCreatedEvent;
import com.hrbatovic.micronaut.master.product.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.micronaut.master.product.messaging.model.in.UserUpdatedEvent;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.util.UUID;

@KafkaListener(groupId = "product-events-group")
public class MessageConsumer {

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Topic("user-registered")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = MapUtil.INSTANCE.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @Topic("user-updated")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        MapUtil.INSTANCE.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.update(userEntity);

    }

    @Topic("user-deleted")
    public void onUserDeleted(UUID id){
        System.out.println("Recieved user-deleted-in event: " + id);
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }

    @Topic("license-template-created")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent){
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        ProductEntity productEntity = productRepository.findById(licenseTemplateCreatedEvent.getLicenseTemplate().getId()).orElse(null);
        if(productEntity == null){
            return;
        }
        productEntity.setLicenseAvailable(true);
        productRepository.update(productEntity);

    }

    @Topic("license-template-deleted")
    public void onLicenseTemplateDeleted(UUID productId){
        System.out.println("Recieved license-template-deleted-in event: " + productId);

        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(true);
        productRepository.update(productEntity);

    }

}
