package com.hrbatovic.quarkus.master.notification.messaging.consumers;

import com.hrbatovic.quarkus.master.notification.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.notification.mapper.MapUtil;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.*;
import com.hrbatovic.quarkus.master.notification.messaging.model.out.OrderNotificationSentEvent;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
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
    Mailer mailer;

    @Inject
    MapUtil mapper;

    @Inject
    ManagedExecutor executor;

    @Inject
    @Channel("order-notification-sent-out")
    Emitter<OrderNotificationSentEvent> orderNotificationSentEmitter;

    @Incoming("licenses-generated-in")
    public void onLicensesGenerated(LicenseGeneratedEvent licenseGeneratedEvent){
        System.out.println("Recieved licenses-generated-in event: " + licenseGeneratedEvent);
        executor.runAsync(() -> {
            sendOrderConfirmation(licenseGeneratedEvent);;
            orderNotificationSentEmitter.send(buildOrderNotificationSentEvent(licenseGeneratedEvent));
        });

    }

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        executor.runAsync(() -> {
            if (UserEntity.findById(userRegisteredEvent.getUserPayload().getId()) != null) {
                return;
            }
            UserEntity userEntity = mapper.map(userRegisteredEvent.getUserPayload());

            userEntity.persist();
        });
    }

    public void sendOrderConfirmation(LicenseGeneratedEvent licenseGeneratedEvent){
        UserEntity userEntity = UserEntity.findById(licenseGeneratedEvent.getUserId());

        mailer.send(Mail.withText(userEntity.getEmail(), "License delivery, order - " + licenseGeneratedEvent.getOrderId(), "Hi " + userEntity.getFirstName() + " " + userEntity.getLastName() + ", your licenses are available: " + licenseGeneratedEvent.getLicenses().toString()));
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

    @Incoming("user-credentials-updated-in")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event: " + userCredentialsUpdatedEvent);

        executor.runAsync(()->{
            UserEntity userEntity = UserEntity.findById(userCredentialsUpdatedEvent.getId());
            if(userEntity == null){
                return;
            }

            mapper.update(userEntity, userCredentialsUpdatedEvent);
            userEntity.update();
        });
    }

    @Incoming("payment-fail-in")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);
        executor.runAsync(() -> {
            UserEntity userEntity = UserEntity.findById(paymentFailEvent.getPaymentPayload().getUserId());
            if (userEntity == null) {
                return;
            }

            mailer.send(Mail.withText(userEntity.getEmail(), "Payment failed - order:" + paymentFailEvent.getPaymentPayload().getOrderId(), "Hello " + userEntity.getFirstName() + " " + userEntity.getLastName() + ", there was a problem with your payment: " + paymentFailEvent.getMessage()));
        });
    }

    private static OrderNotificationSentEvent buildOrderNotificationSentEvent(LicenseGeneratedEvent licenseGeneratedEvent) {
        return new OrderNotificationSentEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(licenseGeneratedEvent.getUserEmail())
                .setSessionId(licenseGeneratedEvent.getSessionId())
                .setUserId(licenseGeneratedEvent.getUserId())
                .setOrderId(licenseGeneratedEvent.getOrderId());
    }
}
