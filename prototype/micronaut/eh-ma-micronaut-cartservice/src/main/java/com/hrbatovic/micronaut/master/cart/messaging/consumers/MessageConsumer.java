package com.hrbatovic.micronaut.master.cart.messaging.consumers;

import com.hrbatovic.micronaut.master.cart.db.entities.CartEntity;
import com.hrbatovic.micronaut.master.cart.db.entities.ProductEntity;
import com.hrbatovic.micronaut.master.cart.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.cart.db.repositories.CartRepository;
import com.hrbatovic.micronaut.master.cart.db.repositories.ProductRepository;
import com.hrbatovic.micronaut.master.cart.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.cart.mapper.MapUtil;
import com.hrbatovic.micronaut.master.cart.messaging.model.in.*;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.util.UUID;

@KafkaListener(groupId = "cart-events-group")
public class MessageConsumer {

    @Inject
    UserRepository userRepository;

    @Inject
    CartRepository cartRepository;

    @Inject
    ProductRepository productRepository;

    @Topic("product-created")
    public void onProductCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Recieved product-created-in event: " + productCreatedEvent);
        if(productRepository.findById(productCreatedEvent.getProduct().getId()).orElse(null) != null){
            return;
        }

        ProductEntity productEntity = MapUtil.INSTANCE.map(productCreatedEvent.getProduct());
        productRepository.save(productEntity);
    }

    @Topic("product-updated")
    public void onProductUpdated(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Recieved product-updated-in event: " + productUpdatedEvent);
        ProductEntity pE = productRepository.findById(productUpdatedEvent.getProduct().getId()).orElse(null);
        if (pE == null) {
            return;
        }

        ProductEntity productEntity = MapUtil.INSTANCE.map(productUpdatedEvent.getProduct());

        productRepository.update(productEntity);
    }

    @Topic("product-deleted")
    public void onProductDeleted(UUID id) {
        System.out.println("Recieved product-deleted-in event: " + id);

        ProductEntity pE = productRepository.findById(id).orElse(null);
        if (pE == null) {
            return;
        }

        pE.setDeleted(true);
        productRepository.update(pE);
    }

    @Topic("order-created")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);
        CartEntity cartEntity = cartRepository.findById(orderCreatedEvent.getOrder().getId()).orElse(null);
        if(cartEntity == null){
            return;
        }
        cartRepository.delete(cartEntity);
    }


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
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent) {
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        ProductEntity productEntity = productRepository.findById(licenseTemplateCreatedEvent.getLicenseTemplate().getProductId()).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(true);
        productRepository.update(productEntity);
    }

    @Topic("license-template-deleted")
    public void onLicenseTemplateDeleted(UUID productId) {
        System.out.println("Recieved license-template-deleted-in event: " + productId);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if(productEntity == null){
            return;
        }

        productEntity.setLicenseAvailable(false);
        productRepository.update(productEntity);
    }

}
