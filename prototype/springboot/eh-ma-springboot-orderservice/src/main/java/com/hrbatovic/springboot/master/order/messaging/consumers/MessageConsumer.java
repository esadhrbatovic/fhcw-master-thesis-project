package com.hrbatovic.springboot.master.order.messaging.consumers;

import com.hrbatovic.master.springboot.order.model.Order;
import com.hrbatovic.springboot.master.order.db.entities.OrderEntity;
import com.hrbatovic.springboot.master.order.db.entities.UserEntity;
import com.hrbatovic.springboot.master.order.db.repositories.OrderRepository;
import com.hrbatovic.springboot.master.order.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.order.mapper.MapUtil;
import com.hrbatovic.springboot.master.order.messaging.model.in.*;
import com.hrbatovic.springboot.master.order.messaging.model.out.OrderCreatedEvent;
import com.hrbatovic.springboot.master.order.messaging.producers.OrderCreatedEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderCreatedEventProducer orderCreatedEventProducer;

    @Autowired
    MapUtil mapper;


    @KafkaListener(groupId = "order-group", topics = "checkout-started", containerFactory = "checkoutStartedFactory")
    public void onCheckoutStarted(CheckoutStartedEvent checkoutStartedEvent) {
        try{
            System.out.println("Recieved checkout-started event: " + checkoutStartedEvent);

            System.out.println("In try block");
            OrderEntity orderEntity = orderRepository.findById(checkoutStartedEvent.getCart().getId()).orElse(null);
            System.out.println("Fetched mongodb");

            if (orderEntity != null) {
                return;
            }
            System.out.println("Order not there");

            orderEntity = mapper.map(checkoutStartedEvent.getCart());
            System.out.println("mapped to new order entity");
            orderEntity.setPaymentMethod(checkoutStartedEvent.getPaymentMethod());
            System.out.println("mapped paymentmethod");
            orderEntity.setPaymentToken(checkoutStartedEvent.getPaymentToken());
            System.out.println("saved order entity");
            orderRepository.save(orderEntity);
            System.out.println("saved order entity");

            OrderCreatedEvent orderCreatedEvent = buildOrderCreatedEvent(checkoutStartedEvent, orderEntity);
            System.out.println("constructed order created event");
            orderCreatedEventProducer.send(orderCreatedEvent);
            System.out.println("order-created event sent");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @KafkaListener(groupId = "order-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }


    @KafkaListener(groupId = "order-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        userEntity.setRole(userUpdatedEvent.getUserPayload().getRole());
        userRepository.save(userEntity);
    }



    @KafkaListener(groupId = "order-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UserDeletedEvent userDeletedEvent){
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);
        UserEntity userEntity = userRepository.findById(userDeletedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }


    @KafkaListener(groupId = "order-group", topics = "payment-success", containerFactory = "paymentSuccessFactory")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEventPayload) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEventPayload);
        OrderEntity orderEntity = orderRepository.findById(paymentSuccessEventPayload.getPaymentPayload().getOrderId()).orElse(null);
        orderEntity.setStatus(Order.StatusEnum.PAYMENT_COMPLETED.toString());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(orderEntity);
    }

    @KafkaListener(groupId = "order-group", topics = "payment-fail", containerFactory = "paymentFailFactory")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);
        OrderEntity orderEntity = orderRepository.findById(paymentFailEvent.getPaymentPayload().getOrderId()).orElse(null);
        orderEntity.setStatus(Order.StatusEnum.PAYMENT_FAILED.toString());
        orderEntity.setStatusDetail(paymentFailEvent.getMessage());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(orderEntity);
    }


    @KafkaListener(groupId = "order-group", topics = "licenses-generated", containerFactory = "licensesGeneratedFactory")
    public void onLicensesGenerated(LicensesGeneratedEvent licensesGeneratedEventPayload) {
        System.out.println("Recieved licenses-generated-in event: " + licensesGeneratedEventPayload);
        OrderEntity orderEntity = orderRepository.findById(licensesGeneratedEventPayload.getOrderId()).orElse(null);
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setStatus(Order.StatusEnum.LICENSES_GENERATED.toString());
        orderRepository.save(orderEntity);
    }

    @KafkaListener(groupId = "order-group", topics = "order-notification-sent", containerFactory = "orderNotificationSentFactory")
    public void onOrderNotificationSent(OrderNotificationSentEvent orderNotificationSentEventPayload) {
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEventPayload);
        OrderEntity orderEntity = orderRepository.findById(orderNotificationSentEventPayload.getOrderId()).orElse(null);
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setStatus(Order.StatusEnum.COMPLETED.toString());
        orderRepository.save(orderEntity);
    }


    private OrderCreatedEvent buildOrderCreatedEvent(CheckoutStartedEvent checkoutStartedEvent, OrderEntity orderEntity) {
        return new OrderCreatedEvent()
                .setUserId(checkoutStartedEvent.getUserId())
                .setUserEmail(checkoutStartedEvent.getUserEmail())
                .setTimestamp(LocalDateTime.now())
                .setSessionId(checkoutStartedEvent.getSessionId())
                .setRequestCorrelationId(checkoutStartedEvent.getRequestCorrelationId())
                .setOrder(mapper.toOrderPayload(orderEntity));
    }

}
