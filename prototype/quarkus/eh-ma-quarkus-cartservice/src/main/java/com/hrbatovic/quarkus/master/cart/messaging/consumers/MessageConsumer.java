package com.hrbatovic.quarkus.master.cart.messaging.consumers;

import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.cart.mapper.MapUtil;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.*;
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


    @Incoming("product-created-in")
    public void onProductCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Recieved product-created-in event: " + productCreatedEvent);

        executor.runAsync(()->{
            if(ProductEntity.findById(productCreatedEvent.getProduct().getId()) != null){
                return;
            }

            ProductEntity productEntity = mapper.map(productCreatedEvent.getProduct());

            productEntity.persist();
        });
    }

    @Incoming("product-updated-in")
    public void onProductUpdated(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Recieved product-updated-in event: " + productUpdatedEvent);

        executor.runAsync(()->{
            ProductEntity pE = ProductEntity.findById(productUpdatedEvent.getProduct().getId());
            if (pE == null) {
                return;
            }

            ProductEntity productEntity = mapper.map(productUpdatedEvent.getProduct());
            productEntity.update();
        });
    }

    @Incoming("product-deleted-in")
    public void onProductDeleted(UUID id) {
        System.out.println("Recieved product-deleted-in event: " + id);

        executor.runAsync(()->{
            ProductEntity pE = ProductEntity.findById(id);
            if (pE == null) {
                return;
            }

            pE.setDeleted(true);
            pE.update();
        });
    }

    @Incoming("order-created-in")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);

        executor.runAsync(()->{
            CartEntity cartEntity = CartEntity.findById(orderCreatedEvent.getOrder().getId());
            if(cartEntity == null){
                return;
            }

            cartEntity.delete();
        });
    }

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
