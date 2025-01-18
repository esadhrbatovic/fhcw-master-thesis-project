package com.hrbatovic.springboot.master.payment.messaging.consumers;

import com.hrbatovic.springboot.master.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.springboot.master.payment.db.entities.UserEntity;
import com.hrbatovic.springboot.master.payment.db.repositories.PaymentMethodRepository;
import com.hrbatovic.springboot.master.payment.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.payment.mapper.MapUtil;
import com.hrbatovic.springboot.master.payment.messaging.model.in.OrderCreatedEvent;
import com.hrbatovic.springboot.master.payment.messaging.model.in.UserDeletedEvent;
import com.hrbatovic.springboot.master.payment.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.springboot.master.payment.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.springboot.master.payment.messaging.model.out.PaymentFailEvent;
import com.hrbatovic.springboot.master.payment.messaging.model.out.PaymentSuccessEvent;
import com.hrbatovic.springboot.master.payment.messaging.producers.PaymentFailEventProducer;
import com.hrbatovic.springboot.master.payment.messaging.producers.PaymentSuccessEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageConsumer {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    PaymentSuccessEventProducer paymentSuccessEventProducer;

    @Autowired
    PaymentFailEventProducer paymentFailEventProducer;

    @Autowired
    MapUtil mapper;


    @KafkaListener(groupId = "payment-group", topics = "order-created", containerFactory = "orderCreatedFactory")
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


    @KafkaListener(groupId = "payment-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "payment-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.save(userEntity);

    }

    @KafkaListener(groupId = "payment-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UserDeletedEvent userDeletedEvent){
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);
        UserEntity userEntity = userRepository.findById(userDeletedEvent.getId()).orElse(null);
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
