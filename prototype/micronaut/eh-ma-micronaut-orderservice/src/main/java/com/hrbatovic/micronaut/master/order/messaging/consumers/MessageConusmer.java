package com.hrbatovic.micronaut.master.order.messaging.consumers;

import com.hrbatovic.micronaut.master.order.db.entities.OrderEntity;
import com.hrbatovic.micronaut.master.order.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.order.db.repositories.OrderRepository;
import com.hrbatovic.micronaut.master.order.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.order.mapper.MapUtil;
import com.hrbatovic.micronaut.master.order.messaging.model.in.*;
import com.hrbatovic.micronaut.master.order.messaging.model.out.OrderCreatedEvent;
import com.hrbatovic.micronaut.master.order.messaging.producers.OrderCreatedEventProducer;
import com.hrbatovic.micronaut.master.order.model.OrderStatus;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.UUID;

@KafkaListener(groupId = "cart-events-group")
public class MessageConusmer {

    @Inject
    UserRepository userRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderCreatedEventProducer orderCreatedEventProducer;

    @Topic("checkout-started")
    public void onCheckoutStarted(CheckoutStartedEvent checkoutStartedEvent) {
        OrderEntity orderEntity = orderRepository.findById(checkoutStartedEvent.getCart().getId()).orElse(null);

        if (orderEntity != null) {
            return;
        }

        orderEntity = MapUtil.INSTANCE.map(checkoutStartedEvent.getCart());
        orderEntity.setPaymentMethod(checkoutStartedEvent.getPaymentMethod());
        orderEntity.setPaymentToken(checkoutStartedEvent.getPaymentToken());
        orderRepository.save(orderEntity);

        OrderCreatedEvent orderCreatedEvent = buildOrderCreatedEvent(checkoutStartedEvent, orderEntity);
        orderCreatedEventProducer.send(orderCreatedEvent);
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

        userEntity.setRole(userUpdatedEvent.getUserPayload().getRole());
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

    @Topic("payment-success")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEventPayload) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEventPayload);
        OrderEntity orderEntity = orderRepository.findById(paymentSuccessEventPayload.getPaymentPayload().getOrderId()).orElse(null);
        orderEntity.setStatus(OrderStatus.PAYMENT_COMPLETED.toString());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderRepository.update(orderEntity);
    }

    @Topic("payment-fail")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);
        OrderEntity orderEntity = orderRepository.findById(paymentFailEvent.getPaymentPayload().getOrderId()).orElse(null);
        orderEntity.setStatus(OrderStatus.PAYMENT_FAILED.toString());
        orderEntity.setStatusDetail(paymentFailEvent.getMessage());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderRepository.update(orderEntity);
    }

    @Topic("licenses-generated")
    public void onLicensesGenerated(LicensesGeneratedEvent licensesGeneratedEventPayload) {
        System.out.println("Recieved licenses-generated-in event: " + licensesGeneratedEventPayload);
        OrderEntity orderEntity = orderRepository.findById(licensesGeneratedEventPayload.getOrderId()).orElse(null);
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setStatus(OrderStatus.LICENSES_GENERATED.toString());
        orderRepository.update(orderEntity);

    }

    @Topic("order-notification-sent")
    public void onOrderNotificationSent(OrderNotificationSentEvent orderNotificationSentEventPayload) {
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEventPayload);
        OrderEntity orderEntity = orderRepository.findById(orderNotificationSentEventPayload.getOrderId()).orElse(null);
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setStatus(OrderStatus.COMPLETED.toString());
        orderRepository.update(orderEntity);
    }

    private OrderCreatedEvent buildOrderCreatedEvent(CheckoutStartedEvent checkoutStartedEvent, OrderEntity orderEntity) {
        return new OrderCreatedEvent()
                .setUserId(checkoutStartedEvent.getUserId())
                .setUserEmail(checkoutStartedEvent.getUserEmail())
                .setTimestamp(LocalDateTime.now())
                .setSessionId(checkoutStartedEvent.getSessionId())
                .setRequestCorrelationId(checkoutStartedEvent.getRequestCorrelationId())
                .setOrder(MapUtil.INSTANCE.toOrderPayload(orderEntity));
    }

}
