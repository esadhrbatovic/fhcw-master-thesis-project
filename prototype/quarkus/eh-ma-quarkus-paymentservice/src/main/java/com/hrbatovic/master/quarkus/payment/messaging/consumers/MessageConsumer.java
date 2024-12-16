package com.hrbatovic.master.quarkus.payment.messaging.consumers;

import com.aayushatharva.brotli4j.common.annotations.Local;
import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.payment.mapper.MapUtil;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.OrderCreatedEvent;
import com.hrbatovic.master.quarkus.payment.messaging.model.out.PaymentFailEvent;
import com.hrbatovic.master.quarkus.payment.messaging.model.out.PaymentSuccessEvent;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.UserUpdatedEvent;
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
    MapUtil mapper;

    @Inject
    @Channel("payment-success-out")
    Emitter<PaymentSuccessEvent> paymentSuccessEmitter;

    @Inject
    @Channel("payment-fail-out")
    Emitter<PaymentFailEvent> paymentFailEmitter;

    @Incoming("order-created-in")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);

        executor.runAsync(()->{
            PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.find("selector", orderCreatedEvent.getOrder().getPaymentMethodSelector()).firstResult();

            //TODO: define constant for error messages
            if(paymentMethodEntity == null){
                System.out.println("Selected payment method not available");
                paymentFailEmitter.send(buildPaymentFailEvent(orderCreatedEvent));
                return;
            }
            paymentSuccessEmitter.send(buildPaymentSuccessEvent(orderCreatedEvent));
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
        System.out.println("Recieved user-deleted-in event: " + id);
        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(id);
            if (userEntity == null) {
                return;
            }

            userEntity.delete();
        });
    }

    private PaymentSuccessEvent buildPaymentSuccessEvent(OrderCreatedEvent orderCreatedEvent) {
        return new PaymentSuccessEvent().
                setTimestamp(LocalDateTime.now())
                .setUserId(orderCreatedEvent.getUserId())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setSessionId(orderCreatedEvent.getSessionId())
                .setOrder(mapper.map(orderCreatedEvent.getOrder()));
    }

    private PaymentFailEvent buildPaymentFailEvent(OrderCreatedEvent orderCreatedEvent) {
        return new PaymentFailEvent()
                .setMessage("Selected Payment method not available")
                .setUserId(orderCreatedEvent.getUserId())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setSessionId(orderCreatedEvent.getSessionId())
                .setTimestamp(LocalDateTime.now())
                .setOrder(mapper.map(orderCreatedEvent.getOrder()));
    }

}
