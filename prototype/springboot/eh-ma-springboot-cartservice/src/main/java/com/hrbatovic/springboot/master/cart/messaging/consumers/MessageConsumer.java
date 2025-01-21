package com.hrbatovic.springboot.master.cart.messaging.consumers;

import com.hrbatovic.springboot.master.cart.db.entities.CartEntity;
import com.hrbatovic.springboot.master.cart.db.entities.ProductEntity;
import com.hrbatovic.springboot.master.cart.db.entities.UserEntity;
import com.hrbatovic.springboot.master.cart.db.repositories.CartRepository;
import com.hrbatovic.springboot.master.cart.db.repositories.ProductRepository;
import com.hrbatovic.springboot.master.cart.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.cart.mapper.MapUtil;
import com.hrbatovic.springboot.master.cart.messaging.model.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MapUtil mapper;

    @KafkaListener(groupId = "cart-group", topics = "product-created", containerFactory = "productCreatedFactory")
    public void onProductCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Recieved product-created-in event: " + productCreatedEvent);
        if(productRepository.findById(productCreatedEvent.getProduct().getId()).orElse(null) != null){
            return;
        }

        ProductEntity productEntity = mapper.map(productCreatedEvent.getProduct());
        productRepository.save(productEntity);
    }

    @KafkaListener(groupId = "cart-group", topics = "product-updated", containerFactory = "productUpdatedFactory")
    public void onProductUpdated(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Recieved product-updated-in event: " + productUpdatedEvent);
        ProductEntity pE = productRepository.findById(productUpdatedEvent.getProduct().getId()).orElse(null);
        if (pE == null) {
            return;
        }

        ProductEntity productEntity = mapper.map(productUpdatedEvent.getProduct());

        productRepository.save(productEntity);
    }

    @KafkaListener(groupId = "cart-group", topics = "product-deleted", containerFactory = "productDeletedFactory")
    public void onProductDeleted(ProductDeletedEvent productDeletedEvent) {
        System.out.println("Recieved product-deleted-in event: " + productDeletedEvent);

        ProductEntity pE = productRepository.findById(productDeletedEvent.getId()).orElse(null);
        if (pE == null) {
            return;
        }

        pE.setDeleted(true);
        productRepository.save(pE);
    }


    @KafkaListener(groupId = "cart-group", topics = "order-created", containerFactory = "orderCreatedFactory")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);
        CartEntity cartEntity = cartRepository.findById(orderCreatedEvent.getOrder().getId()).orElse(null);
        if(cartEntity == null){
            return;
        }
        cartRepository.delete(cartEntity);
    }


    @KafkaListener(groupId = "cart-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }


    @KafkaListener(groupId = "cart-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "cart-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UUID id){
        System.out.println("Recieved user-deleted-in event: " + id);
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }

    @KafkaListener(groupId = "cart-group", topics = "license-template-created", containerFactory = "licenseTemplateCreatedFactory")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent) {
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        ProductEntity productEntity = productRepository.findById(licenseTemplateCreatedEvent.getLicenseTemplate().getProductId()).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(true);
        productRepository.save(productEntity);
    }

    @KafkaListener(groupId = "cart-group", topics = "license-template-deleted", containerFactory = "licenseTemplateDeletedFactory")
    public void onLicenseTemplateDeleted(UUID productId) {
        System.out.println("Recieved license-template-deleted-in event: " + productId);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(false);
        productRepository.save(productEntity);
    }
}
