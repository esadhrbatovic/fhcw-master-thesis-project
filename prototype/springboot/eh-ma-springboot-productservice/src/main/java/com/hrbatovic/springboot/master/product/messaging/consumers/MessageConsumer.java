package com.hrbatovic.springboot.master.product.messaging.consumers;

import com.hrbatovic.springboot.master.product.db.entities.ProductEntity;
import com.hrbatovic.springboot.master.product.db.entities.UserEntity;
import com.hrbatovic.springboot.master.product.db.repositories.ProductRepository;
import com.hrbatovic.springboot.master.product.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.product.mapper.MapUtil;
import com.hrbatovic.springboot.master.product.messaging.model.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MapUtil mapper;

    @KafkaListener(groupId = "product-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }


    @KafkaListener(groupId = "product-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "product-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UserDeletedEvent userDeletedEvent){
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);
        UserEntity userEntity = userRepository.findById(userDeletedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }

    @KafkaListener(groupId = "product-group", topics = "license-template-created", containerFactory = "licenseTemplateCreatedFactory")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent){
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        ProductEntity productEntity = productRepository.findById(licenseTemplateCreatedEvent.getLicenseTemplate().getProductId()).orElse(null);
        if(productEntity == null){
            return;
        }
        productEntity.setLicenseAvailable(true);
        productRepository.save(productEntity);
    }


    @KafkaListener(groupId = "product-group", topics = "license-template-deleted", containerFactory = "licenseTemplateDeletedFactory")
    public void onLicenseTemplateDeleted(LicenseTemplateDeletedEvent licenseTemplateDeletedEvent){
        System.out.println("Recieved license-template-deleted-in event: " + licenseTemplateDeletedEvent);

        ProductEntity productEntity = productRepository.findById(licenseTemplateDeletedEvent.getId()).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(false);
        productRepository.save(productEntity);

    }

}
