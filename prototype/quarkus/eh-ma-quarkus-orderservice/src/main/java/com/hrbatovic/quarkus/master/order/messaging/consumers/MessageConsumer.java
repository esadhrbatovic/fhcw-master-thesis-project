package com.hrbatovic.quarkus.master.order.messaging.consumers;

import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.order.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.order.mapper.MapUtil;
import com.hrbatovic.quarkus.master.order.messaging.model.*;
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
    Emitter<OrderCreatedEventPayload> orderCreatedEmitter;

    @Incoming("checkout-started-in")
    public void onCheckoutStarted(CheckoutStartedEventPayload checkoutStartedEventPayload){
        System.out.println("Recieved checkout-started-in event: " + checkoutStartedEventPayload);

        OrderEntity orderEntity = OrderEntity.findById(checkoutStartedEventPayload.getCartEntity().getId());
        if(orderEntity != null) {
            return; //order already created
        }

        orderEntity = new OrderEntity(checkoutStartedEventPayload.getCartEntity().getId());
        orderEntity.setOrderItems(mapper.toOrderItemEntityList(checkoutStartedEventPayload.getCartEntity().getCartProducts()));
        orderEntity.setCurrency("EUR");
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUserId(checkoutStartedEventPayload.getCartEntity().getUserId());
        orderEntity.setStatus(Order.StatusEnum.OPEN.toString());
        orderEntity.setTotalAmount(checkoutStartedEventPayload.getCartEntity().getTotalPrice());
        orderEntity.setPaymenToken(checkoutStartedEventPayload.getPaymentToken());

        orderEntity.persist();

        OrderCreatedEventPayload orderCreatedEventPayload = new OrderCreatedEventPayload();
        orderCreatedEventPayload.setOrderEntity(orderEntity);
        orderCreatedEmitter.send(orderCreatedEventPayload);
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

    @Incoming("payment-success-in")
    public void onPaymentSuccess(PaymentSuccessEventPayload paymentSuccessEventPayload) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = paymentSuccessEventPayload.getOrderEntity();
            orderEntity.setStatus(Order.StatusEnum.PAYMENT_COMPLETED.toString());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.update();
        });
    }


    @Incoming("licenses-generated-in")
    public void onLicensesGenerated(LicensesGeneratedEventPayload licensesGeneratedEventPayload){
        System.out.println("Recieved licenses-generated-in event: " + licensesGeneratedEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(licensesGeneratedEventPayload.getOrderId());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.setStatus(Order.StatusEnum.LICENSES_GENERATED.toString());
            orderEntity.update();
        });

    }


    @Incoming("order-notification-sent-in")
    public void onOrderNotificationSent(OrderNotificationSentEventPayload orderNotificationSentEventPayload){
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEventPayload);

        executor.runAsync(() -> {
            OrderEntity orderEntity = OrderEntity.findById(orderNotificationSentEventPayload.getOrderId());
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderEntity.setStatus(Order.StatusEnum.COMPLETED.toString());
            orderEntity.update();
        });

    }


}
