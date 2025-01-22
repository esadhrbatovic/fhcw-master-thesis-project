package com.hrbatovic.springboot.master.notification.messaging.consumers;


import com.hrbatovic.springboot.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.springboot.master.notification.db.entities.UserEntity;
import com.hrbatovic.springboot.master.notification.db.repositories.NotificationRepository;
import com.hrbatovic.springboot.master.notification.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.notification.mapper.MapUtil;
import com.hrbatovic.springboot.master.notification.messaging.model.in.*;
import com.hrbatovic.springboot.master.notification.messaging.model.out.OrderNotificationSentEvent;
import com.hrbatovic.springboot.master.notification.messaging.producers.OrderNotificationSentEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageConsumers {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    OrderNotificationSentEventProducer orderNotificationSentEventProducer;

    @Autowired
    MapUtil mapper;


    @Autowired
    private JavaMailSender emailSender;

    @KafkaListener(groupId = "notification-group", topics = "licenses-generated", containerFactory = "licensesGeneratedFactory")
    public void onLicensesGenerated(LicenseGeneratedEvent licenseGeneratedEvent){
        System.out.println("Recieved licenses-generated-in event: " + licenseGeneratedEvent);
        sendOrderConfirmation(licenseGeneratedEvent);
        orderNotificationSentEventProducer.send(buildOrderNotificationSentEvent(licenseGeneratedEvent));
    }


    @KafkaListener(groupId = "notification-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "notification-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "notification-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UserDeletedEvent userDeletedEvent){
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);
        UserEntity userEntity = userRepository.findById(userDeletedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }

    @KafkaListener(groupId = "notification-group", topics = "user-credentials-updated", containerFactory = "userCredentialsUpdatedFactory")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event: " + userCredentialsUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userCredentialsUpdatedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userCredentialsUpdatedEvent);
        userRepository.save(userEntity);

    }

    @KafkaListener(groupId = "notification-group", topics = "payment-fail", containerFactory = "paymentFailFactory")
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

        sendMail(notificationEntity.getEmail(), notificationEntity.getSubject(), notificationEntity.getBody());
    }

    public void sendOrderConfirmation(LicenseGeneratedEvent licenseGeneratedEvent){
        UserEntity userEntity = userRepository.findById(licenseGeneratedEvent.getUserId()).orElse(null);

        if (userEntity == null) {
            return;
        }

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setEmail(userEntity.getEmail());
        notificationEntity.setSubject("License delivery, order - " + licenseGeneratedEvent.getOrderId());
        notificationEntity.setBody("Hi " + userEntity.getFirstName() + " " + userEntity.getLastName() + ", your licenses are available: " + licenseGeneratedEvent.getLicenses().toString());
        notificationEntity.setSentAt(LocalDateTime.now());
        notificationEntity.setUserId(userEntity.getId());
        notificationEntity.setType("order-completed");
        notificationRepository.save(notificationEntity);

        sendMail(notificationEntity.getEmail(), notificationEntity.getSubject(), notificationEntity.getBody());
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


    public void sendMail(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("g2c.admin@hrbatovic.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
