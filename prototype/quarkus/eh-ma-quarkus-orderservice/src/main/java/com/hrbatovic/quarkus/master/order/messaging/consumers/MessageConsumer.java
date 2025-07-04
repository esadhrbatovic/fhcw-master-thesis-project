package com.hrbatovic.quarkus.master.order.messaging.consumers;

import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.order.mapper.MapUtil;
import com.hrbatovic.quarkus.master.order.messaging.model.in.*;
import com.hrbatovic.quarkus.master.order.messaging.model.out.OrderCreatedEvent;
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
    MapUtil mapper;

    @Inject
    ManagedExecutor executor;

    @Inject
    @Channel("order-created-out")
    Emitter<OrderCreatedEvent> orderCreatedEmitter;

    @Incoming("checkout-started-in")
    public void onCheckoutStarted(CheckoutStartedEvent checkoutStartedEvent) {
        System.out.println("Recieved checkout-started-in event: " + checkoutStartedEvent);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(checkoutStartedEvent.getCart().getId());
            if (orderEntity != null) {
                return;
            }

            orderEntity = mapper.map(checkoutStartedEvent.getCart());
            orderEntity.setPaymentMethod(checkoutStartedEvent.getPaymentMethod());
            orderEntity.setPaymentToken(checkoutStartedEvent.getPaymentToken());
            orderEntity.persist();

            OrderCreatedEvent orderCreatedEvent = buildOrderCreatedEvent(checkoutStartedEvent, orderEntity);

            orderCreatedEmitter.send(orderCreatedEvent);
        });
    }


    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event:" + userRegisteredEvent);

        executor.runAsync(() -> {
            if (UserEntity.findById(userRegisteredEvent.getUserPayload().getId()) != null) {
                return;
            }

            UserEntity userEntity = mapper.map(userRegisteredEvent.getUserPayload());

            userEntity.persist();
        });
    }

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(userUpdatedEvent.getUserPayload().getId());
            if (userEntity == null) {
                return;
            }

            userEntity.setRole(userUpdatedEvent.getUserPayload().getRole());
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

    @Incoming("payment-success-in")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEventPayload) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(paymentSuccessEventPayload.getPaymentPayload().getOrderId());
            orderEntity.setStatus(Order.StatusEnum.PAYMENT_COMPLETED.toString());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.update();
        });
    }

    @Incoming("payment-fail-in")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(paymentFailEvent.getPaymentPayload().getOrderId());
            orderEntity.setStatus(Order.StatusEnum.PAYMENT_FAILED.toString());
            orderEntity.setStatusDetail(paymentFailEvent.getMessage());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.update();
        });
    }


    @Incoming("licenses-generated-in")
    public void onLicensesGenerated(LicensesGeneratedEvent licensesGeneratedEventPayload) {
        System.out.println("Recieved licenses-generated-in event: " + licensesGeneratedEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(licensesGeneratedEventPayload.getOrderId());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.setStatus(Order.StatusEnum.LICENSES_GENERATED.toString());
            orderEntity.update();
        });

    }


    @Incoming("order-notification-sent-in")
    public void onOrderNotificationSent(OrderNotificationSentEvent orderNotificationSentEventPayload) {
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(orderNotificationSentEventPayload.getOrderId());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.setStatus(Order.StatusEnum.COMPLETED.toString());
            orderEntity.update();
        });

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
