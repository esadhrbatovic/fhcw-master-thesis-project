package com.hrbatovic.micronaut.master.notification.messaging.consumers;

import com.hrbatovic.micronaut.master.notification.MailSender;
import com.hrbatovic.micronaut.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.micronaut.master.notification.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.notification.db.repositories.NotificationRepository;
import com.hrbatovic.micronaut.master.notification.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.notification.mapper.MapUtil;
import com.hrbatovic.micronaut.master.notification.messaging.model.in.*;
import com.hrbatovic.micronaut.master.notification.messaging.model.out.OrderNotificationSentEvent;
import com.hrbatovic.micronaut.master.notification.messaging.producers.OrderNotificationSentEventProducer;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.UUID;

@KafkaListener(groupId = "notification-events-group")
public class MessageConsumer {

    @Inject
    UserRepository userRepository;

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    OrderNotificationSentEventProducer orderNotificationSentEventProducer;

    @Inject
    MapUtil mapper;

    @Inject
    MailSender mailSender;

    @Topic("licenses-generated")
    public void onLicensesGenerated(LicenseGeneratedEvent licenseGeneratedEvent){
        System.out.println("Recieved licenses-generated-in event: " + licenseGeneratedEvent);
        sendOrderConfirmation(licenseGeneratedEvent);
        orderNotificationSentEventProducer.send(buildOrderNotificationSentEvent(licenseGeneratedEvent));
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


    @Topic("user-credentials-updated")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event: " + userCredentialsUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userCredentialsUpdatedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userCredentialsUpdatedEvent);
        userRepository.update(userEntity);

    }


    @Topic("payment-fail")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);

        UserEntity userEntity = userRepository.findById(paymentFailEvent.getPaymentPayload().getUserId()).orElse(null);
        if (userEntity == null) {
            return;
        }

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setEmail(userEntity.getEmail());
        notificationEntity.setSubject("Payment failed - order:" + paymentFailEvent.getPaymentPayload().getOrderId());
        notificationEntity.setBody("Hello " + userEntity.getFirstName() + " " + userEntity.getLastName() + ", there was a problem with your payment: " + paymentFailEvent.getMessage());
        notificationEntity.setType("payment-failed");
        notificationEntity.setSentAt(LocalDateTime.now());
        notificationEntity.setUserId(userEntity.getId());
        notificationRepository.save(notificationEntity);

        mailSender.sendEmail(notificationEntity.getEmail(), notificationEntity.getSubject(), notificationEntity.getBody());
    }

    public void sendOrderConfirmation(LicenseGeneratedEvent licenseGeneratedEvent){
        UserEntity userEntity = userRepository.findById(licenseGeneratedEvent.getUserId()).orElse(null);

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setEmail(userEntity.getEmail());
        notificationEntity.setSubject("License delivery, order - " + licenseGeneratedEvent.getOrderId());
        notificationEntity.setBody("Hi " + userEntity.getFirstName() + " " + userEntity.getLastName() + ", your licenses are available: " + licenseGeneratedEvent.getLicenses().toString());
        notificationEntity.setSentAt(LocalDateTime.now());
        notificationEntity.setUserId(userEntity.getId());
        notificationEntity.setType("order-completed");
        notificationRepository.save(notificationEntity);

        mailSender.sendEmail(notificationEntity.getEmail(), notificationEntity.getSubject(), notificationEntity.getBody());
    }

    private static OrderNotificationSentEvent buildOrderNotificationSentEvent(LicenseGeneratedEvent licenseGeneratedEvent) {
        return new OrderNotificationSentEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(licenseGeneratedEvent.getUserEmail())
                .setSessionId(licenseGeneratedEvent.getSessionId())
                .setUserId(licenseGeneratedEvent.getUserId())
                .setRequestCorrelationId(licenseGeneratedEvent.getRequestCorrelationId())
                .setOrderId(licenseGeneratedEvent.getOrderId());


    }
}
