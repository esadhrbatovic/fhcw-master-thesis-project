package com.hrbatovic.quarkus.master.order.messaging.consumers;

import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.order.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.order.messaging.model.CheckoutStartedEventPayload;
import com.hrbatovic.quarkus.master.order.messaging.model.UserUpdatedMsgPayload;
import com.hrbatovic.quarkus.master.order.mapper.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    @Channel("order-created-out")
    Emitter<UUID> orderCreatedEmitter;

    @Incoming("checkout-started-in")
    public void onCheckoutStarted(CheckoutStartedEventPayload checkoutStartedEventPayload){
        System.out.println("Recieved checkout-started-in event: " + checkoutStartedEventPayload);

        OrderEntity orderEntity = OrderEntity.findById(checkoutStartedEventPayload.getCartEntity().getId());
        if(orderEntity != null) {
            return; //order already created
        }

        orderEntity = new OrderEntity(checkoutStartedEventPayload.getCartEntity().getId());
        orderEntity.setOrderItems(Mapper.map(checkoutStartedEventPayload.getCartEntity().getCartProducts()));
        orderEntity.setCurrency("EUR");
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUserId(checkoutStartedEventPayload.getCartEntity().getUserId());
        orderEntity.setStatus("open");
        orderEntity.setTotalAmount(checkoutStartedEventPayload.getCartEntity().getTotalPrice());

        orderEntity.persist();

        orderCreatedEmitter.send(orderEntity.getId());
    }

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


    @Incoming("user-updated-in")
    public void consumeUserUpdated(UserUpdatedMsgPayload userUpdateMsgPayload) {
        System.out.println("Recieved user-updated-in event: " + userUpdateMsgPayload);
        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(userUpdateMsgPayload.getId());
            if (userEntity == null) {
                return;
            }

            userEntity.setRole(userEntity.getRole());
            userEntity.persistOrUpdate();
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

    @Incoming("user-registered-in")
    public void consumeUser(UserEntity userEntity) {
        System.out.println("Recieved user-registered-in event");

        executor.runAsync(() -> {
            if (UserEntity.findById(userEntity.id) != null) {
                return;
            }
            userEntity.persistOrUpdate();
        });
    }


}
