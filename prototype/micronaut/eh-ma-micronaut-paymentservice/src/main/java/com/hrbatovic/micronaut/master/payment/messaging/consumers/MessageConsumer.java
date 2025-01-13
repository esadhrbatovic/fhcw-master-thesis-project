package com.hrbatovic.micronaut.master.payment.messaging.consumers;

import com.hrbatovic.micronaut.master.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.micronaut.master.payment.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.payment.db.repositories.PaymentMethodRepository;
import com.hrbatovic.micronaut.master.payment.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.payment.mapper.MapUtil;
import com.hrbatovic.micronaut.master.payment.messaging.model.in.OrderCreatedEvent;
import com.hrbatovic.micronaut.master.payment.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.micronaut.master.payment.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.micronaut.master.payment.messaging.model.out.PaymentFailEvent;
import com.hrbatovic.micronaut.master.payment.messaging.model.out.PaymentSuccessEvent;
import com.hrbatovic.micronaut.master.payment.messaging.producers.PaymentFailEventProducer;
import com.hrbatovic.micronaut.master.payment.messaging.producers.PaymentSuccessEventProducer;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.UUID;

@KafkaListener(groupId = "payment-events-group")
public class MessageConsumer {

    @Inject
    UserRepository userRepository;

    @Inject
    PaymentMethodRepository paymentMethodRepository;

    @Inject
    PaymentSuccessEventProducer paymentSuccessEventProducer;

    @Inject
    PaymentFailEventProducer paymentFailEventProducer;

    @Inject
    MapUtil mapper;

    @Topic("order-created")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent){
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);

        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findBySelector(orderCreatedEvent.getOrder().getPaymentMethod()).orElse(null);

        if(paymentMethodEntity == null || paymentMethodEntity.getActive().equals(Boolean.FALSE)){
            System.out.println("Selected payment method not available");
            paymentFailEventProducer.send(buildPaymentFailEvent(orderCreatedEvent));
            return;
        }

        paymentSuccessEventProducer.send(buildPaymentSuccessEvent(orderCreatedEvent));

    }

    @Topic("user-registered")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @Topic("user-updated")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
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

    private PaymentSuccessEvent buildPaymentSuccessEvent(OrderCreatedEvent orderCreatedEvent) {
        return new PaymentSuccessEvent().
                setTimestamp(LocalDateTime.now())
                .setUserId(orderCreatedEvent.getUserId())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setSessionId(orderCreatedEvent.getSessionId())
                .setRequestCorrelationId(orderCreatedEvent.getRequestCorrelationId())
                .setPaymentPayload(mapper.map(orderCreatedEvent.getOrder()));
    }

    private PaymentFailEvent buildPaymentFailEvent(OrderCreatedEvent orderCreatedEvent) {
        return new PaymentFailEvent()
                .setMessage("Selected Payment method not available")
                .setUserId(orderCreatedEvent.getUserId())
                .setUserEmail(orderCreatedEvent.getUserEmail())
                .setSessionId(orderCreatedEvent.getSessionId())
                .setTimestamp(LocalDateTime.now())
                .setRequestCorrelationId(orderCreatedEvent.getRequestCorrelationId())
                .setPaymentPayload(mapper.map(orderCreatedEvent.getOrder()));
    }


}
