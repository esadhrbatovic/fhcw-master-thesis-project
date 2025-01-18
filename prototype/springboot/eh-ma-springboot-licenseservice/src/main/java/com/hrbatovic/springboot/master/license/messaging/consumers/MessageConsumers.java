package com.hrbatovic.springboot.master.license.messaging.consumers;

import com.hrbatovic.springboot.master.license.db.entities.LicenseEntity;
import com.hrbatovic.springboot.master.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.springboot.master.license.db.entities.UserEntity;
import com.hrbatovic.springboot.master.license.db.repositories.LicenseRepository;
import com.hrbatovic.springboot.master.license.db.repositories.LicenseTemplateRepository;
import com.hrbatovic.springboot.master.license.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.license.mapper.MapUtil;
import com.hrbatovic.springboot.master.license.messaging.model.in.PaymentSuccessEvent;
import com.hrbatovic.springboot.master.license.messaging.model.in.UserDeletedEvent;
import com.hrbatovic.springboot.master.license.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.springboot.master.license.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.springboot.master.license.messaging.model.in.payload.PaidItemPayload;
import com.hrbatovic.springboot.master.license.messaging.model.out.LicensesGeneratedEvent;
import com.hrbatovic.springboot.master.license.messaging.producers.LicensesGeneratedEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageConsumers {

    @Autowired
    LicenseTemplateRepository licenseTemplateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    LicensesGeneratedEventProducer licensesGeneratedEventProducer;

    @Autowired
    MapUtil mapper;

    @KafkaListener(groupId = "license-group", topics = "payment-success", containerFactory = "paymentSuccessFactory")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEvent);

        List<PaidItemPayload> paidItems = paymentSuccessEvent.getPaymentPayload().getPaidItemPayloads();

        paidItems.forEach(o->generateLicense(o, paymentSuccessEvent.getUserId(), paymentSuccessEvent.getPaymentPayload().getOrderId()));

        List<LicenseEntity> licenses = licenseRepository.findByOrderid(paymentSuccessEvent.getPaymentPayload().getOrderId());

        licensesGeneratedEventProducer.send(buildLicensesGeneratedEvent(paymentSuccessEvent, licenses));
    }

    private void generateLicense(PaidItemPayload paidItem, UUID userId, UUID orderId) {
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(paidItem.getProductId()).orElse(null);

        if (licenseTemplateEntity == null) {
            throw new RuntimeException("No license available for product: " + paidItem.getProductId());
        }

        for (int i = 0; i < paidItem.getQuantity(); i++) {
            LicenseEntity licenseEntity = new LicenseEntity();
            licenseEntity.setProductId(paidItem.getProductId());
            licenseEntity.setProductTitle(paidItem.getProductTitle());
            licenseEntity.setLicenseDuration(licenseTemplateEntity.getLicenseDuration());
            licenseEntity.setIssuedAt(LocalDateTime.now());
            licenseEntity.setOrderId(orderId);
            licenseEntity.setUserId(userId);
            if (licenseEntity.getLicenseDuration() > 0) {
                licenseEntity.setExpiresAt(LocalDateTime.now().plusDays(licenseEntity.getLicenseDuration()));
            } else {
                licenseEntity.setExpiresAt(null);
            }
            licenseRepository.save(licenseEntity);
        }
    }

    @KafkaListener(groupId = "license-group", topics = "user-registered", containerFactory = "userRegisteredFactory")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        UserEntity userEntity = userRepository.findById(userRegisteredEvent.getUserPayload().getId()).orElse(null);
        if(userEntity != null){
            return;
        }

        userEntity = mapper.map(userRegisteredEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "license-group", topics = "user-updated", containerFactory = "userUpdatedFactory")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        UserEntity userEntity = userRepository.findById(userUpdatedEvent.getUserPayload().getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        mapper.update(userEntity, userUpdatedEvent.getUserPayload());
        userRepository.save(userEntity);
    }

    @KafkaListener(groupId = "license-group", topics = "user-deleted", containerFactory = "userDeletedFactory")
    public void onUserDeleted(UserDeletedEvent userDeletedEvent){
        System.out.println("Recieved user-deleted-in event: " + userDeletedEvent);
        UserEntity userEntity = userRepository.findById(userDeletedEvent.getId()).orElse(null);
        if(userEntity == null){
            return;
        }

        userRepository.delete(userEntity);
    }


    private LicensesGeneratedEvent buildLicensesGeneratedEvent(PaymentSuccessEvent paymentSuccessEvent, List<LicenseEntity> licenses) {
        return new LicensesGeneratedEvent()
                .setSessionId(paymentSuccessEvent.getSessionId())
                .setUserEmail(paymentSuccessEvent.getUserEmail())
                .setUserId(paymentSuccessEvent.getUserId())
                .setTimestamp(LocalDateTime.now())
                .setOrderId(paymentSuccessEvent.getPaymentPayload().getOrderId())
                .setRequestCorrelationId(paymentSuccessEvent.getRequestCorrelationId())
                .setLicenses(mapper.map(licenses));
    }

}
