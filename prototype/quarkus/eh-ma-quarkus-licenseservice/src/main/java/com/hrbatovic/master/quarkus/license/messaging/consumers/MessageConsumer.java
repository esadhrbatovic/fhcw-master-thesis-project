package com.hrbatovic.master.quarkus.license.messaging.consumers;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicensesGeneratedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.PaymentSuccessEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.payload.OrderItemPayload;
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

        //executor.runAsync(()->{
            //TODO: check if licenses for order exist, else return

            List<OrderItemPayload> orderItems = paymentSuccessEvent.getOrder().getOrderItems();

            orderItems.forEach(o->generateLicense(o, paymentSuccessEvent.getOrder().getUserId(), paymentSuccessEvent.getOrder().getId()));

            List<LicenseEntity> licenses = LicenseEntity.find("orderId", paymentSuccessEvent.getOrder().getId()).list();
            //TODO: error handling

            LicensesGeneratedEvent licensesGeneratedEvent = new LicensesGeneratedEvent();
            licensesGeneratedEvent.setLicenses(mapper.map(licenses));
            licensesGeneratedEvent.setOrderId(paymentSuccessEvent.getOrder().getId());
            licensesGeneratedEvent.setUserId(paymentSuccessEvent.getOrder().getUserId());
            licensesGeneratedEmitter.send(licensesGeneratedEvent);
        //});

    }

    private void generateLicense(OrderItemPayload orderItem, UUID userId, UUID orderId) {
        LicenseEntity licenseEntity = new LicenseEntity();
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", orderItem.getProductId()).firstResult();

        //TODO: generate quantity times

        licenseEntity.setProductId(orderItem.getProductId());
        licenseEntity.setLicenseDuration(licenseTemplateEntity.getLicenseDuration());
        licenseEntity.setIssuedAt(LocalDateTime.now());
        licenseEntity.setOrderId(orderId);
        licenseEntity.setUserId(userId);
        if (licenseEntity.getLicenseDuration() > 0) {
            licenseEntity.setExpiresAt(LocalDateTime.now().plusDays(licenseEntity.getLicenseDuration()));
        } else {
            licenseEntity.setExpiresAt(null);
        }
        licenseEntity.setActive(true);
        licenseEntity.persist();
    }

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        executor.runAsync(()->{

            if(UserEntity.findById(userRegisteredEvent.getId()) != null){
                return;
            }

            UserEntity userEntity = mapper.map(userRegisteredEvent);

            userEntity.persist();
        });
    }

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);

        executor.runAsync(()->{
            UserEntity userEntity = UserEntity.findById(userUpdatedEvent.getId());
            if(userEntity == null){
                return;
            }

            mapper.update(userEntity, userUpdatedEvent);
            userEntity.update();
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


}
