package com.hrbatovic.micronaut.master.license.messaging.consumers;

import com.hrbatovic.micronaut.master.license.db.entities.LicenseEntity;
import com.hrbatovic.micronaut.master.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.micronaut.master.license.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.license.db.repositories.LicenseRepository;
import com.hrbatovic.micronaut.master.license.db.repositories.LicenseTemplateRepository;
import com.hrbatovic.micronaut.master.license.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.license.mapper.MapUtil;
import com.hrbatovic.micronaut.master.license.messaging.model.in.PaymentSuccessEvent;
import com.hrbatovic.micronaut.master.license.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.micronaut.master.license.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.micronaut.master.license.messaging.model.in.payload.PaidItemPayload;
import com.hrbatovic.micronaut.master.license.messaging.model.out.LicensesGeneratedEvent;
import com.hrbatovic.micronaut.master.license.messaging.producers.LicensesGeneratedEventProducer;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@KafkaListener(groupId = "license-events-group")
public class MessageConsumer {

    @Inject
    LicenseTemplateRepository licenseTemplateRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    LicenseRepository licenseRepository;

    @Inject
    LicensesGeneratedEventProducer licensesGeneratedEventProducer;

    @Topic("payment-success")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEvent);

        List<PaidItemPayload> paidItems = paymentSuccessEvent.getPaymentPayload().getPaidItemPayloads();

        paidItems.forEach(o->generateLicense(o, paymentSuccessEvent.getUserId(), paymentSuccessEvent.getPaymentPayload().getOrderId()));

        List<LicenseEntity> licenses = licenseRepository.findByOrderid(paymentSuccessEvent.getPaymentPayload().getOrderId());

        licensesGeneratedEventProducer.send(buildLicensesGeneratedEvent(paymentSuccessEvent, licenses));
    }

    private void generateLicense(PaidItemPayload paidItem, UUID userId, UUID orderId) {
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(paidItem.getProductId()).orElse(null);

        if(licenseTemplateEntity == null){
            throw new RuntimeException("No license available for product: " + paidItem.getProductId());
        }

        for(int i = 0; i < paidItem.getQuantity(); i++){
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

        MapUtil.INSTANCE.update(userEntity, userUpdatedEvent.getUserPayload());
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

    private LicensesGeneratedEvent buildLicensesGeneratedEvent(PaymentSuccessEvent paymentSuccessEvent, List<LicenseEntity> licenses) {
        return new LicensesGeneratedEvent()
                .setSessionId(paymentSuccessEvent.getSessionId())
                .setUserEmail(paymentSuccessEvent.getUserEmail())
                .setUserId(paymentSuccessEvent.getUserId())
                .setTimestamp(LocalDateTime.now())
                .setOrderId(paymentSuccessEvent.getPaymentPayload().getOrderId())
                .setRequestCorrelationId(paymentSuccessEvent.getRequestCorrelationId())
                .setLicenses(MapUtil.INSTANCE.map(licenses));
    }

}
