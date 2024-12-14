package com.hrbatovic.quarkus.master.cart.messaging.consumers;

import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.cart.mapper.MapUtil;
import com.hrbatovic.quarkus.master.cart.messaging.model.OrderCreatedEventPayload;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.quarkus.master.cart.messaging.model.in.UserUpdatedEvent;
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
    public void onProductCreated(ProductEntity productEntity) {
        System.out.println("Recieved product-created-in event: " + productEntity);

        executor.runAsync(()->{
            if(ProductEntity.findById(productEntity.getId()) != null){
                return;
            }

            productEntity.persistOrUpdate();
        });
    }

    @Incoming("product-updated-in")
    public void onProductUpdated(ProductEntity productEntity) {
        System.out.println("Recieved product-updated-in event: " + productEntity);

        executor.runAsync(()->{
            ProductEntity pE = ProductEntity.findById(productEntity.getId());
            if (pE == null) {
                return;
            }

            productEntity.persistOrUpdate();
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

            pE.delete();
        });
    }

    @Incoming("order-created-in")
    public void onOrderCreated(OrderCreatedEventPayload orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);

        executor.runAsync(()->{
            CartEntity cartEntity = CartEntity.findById(orderCreatedEvent.getOrderEntity().getId());
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

            if(UserEntity.findById(userRegisteredEvent.getId()) != null){
                return;
            }

            UserEntity userEntity = mapper.map(userRegisteredEvent);

            userEntity.persist();
        });
    }

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);

        executor.runAsync(()->{
            UserEntity userEntity = UserEntity.findById(userUpdatedEvent.getId());
            if(userEntity == null){
                return;
            }

            mapper.update(userEntity, userUpdatedEvent);
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

}
