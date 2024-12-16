package com.hrbatovic.master.quarkus.license.messaging.consumers;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.messaging.model.in.payload.PaidItemPayload;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicensesGeneratedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.PaymentSuccessEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserUpdatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    MapUtil mapper;

    @Inject
    @Channel("licenses-generated-out")
    Emitter<LicensesGeneratedEvent> licensesGeneratedEmitter;

    @Incoming("payment-success-in")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEvent);

        executor.runAsync(() -> {
            List<PaidItemPayload> paidItems = paymentSuccessEvent.getPaymentPayload().getPaidItemPayloads();

            paidItems.forEach(o->generateLicense(o, paymentSuccessEvent.getUserId(), paymentSuccessEvent.getPaymentPayload().getOrderId()));

            List<LicenseEntity> licenses = LicenseEntity.find("orderId", paymentSuccessEvent.getPaymentPayload().getOrderId()).list();
            //TODO: error handling

            licensesGeneratedEmitter.send(buildLicensesGeneratedEvent(paymentSuccessEvent, licenses));
        });

    }

    private void generateLicense(PaidItemPayload paidItem, UUID userId, UUID orderId) {
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", paidItem.getProductId()).firstResult();

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
            licenseEntity.persist();
        }

    }

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        executor.runAsync(()->{

            if(UserEntity.findById(userRegisteredEvent.getUserPayload().getId()) != null){
                return;
            }

            UserEntity userEntity = mapper.map(userRegisteredEvent.getUserPayload());

            userEntity.persist();
        });
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

    private LicensesGeneratedEvent buildLicensesGeneratedEvent(PaymentSuccessEvent paymentSuccessEvent, List<LicenseEntity> licenses) {
        return new LicensesGeneratedEvent()
                .setSessionId(paymentSuccessEvent.getSessionId())
                .setUserEmail(paymentSuccessEvent.getUserEmail())
                .setUserId(paymentSuccessEvent.getUserId())
                .setTimestamp(LocalDateTime.now())
                .setOrderId(paymentSuccessEvent.getPaymentPayload().getOrderId())
                .setLicenses(mapper.map(licenses));
    }
}
