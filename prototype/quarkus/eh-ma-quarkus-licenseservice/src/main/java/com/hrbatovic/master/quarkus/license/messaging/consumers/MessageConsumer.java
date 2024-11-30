package com.hrbatovic.master.quarkus.license.messaging.consumers;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.messaging.model.LicensesGeneratedEventPayload;
import com.hrbatovic.master.quarkus.license.messaging.model.OrderItemEntity;
import com.hrbatovic.master.quarkus.license.messaging.model.PaymentSuccessEventPayload;
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
    @Channel("licenses-generated-out")
    Emitter<LicensesGeneratedEventPayload> licensesGeneratedEmitter;

    @Incoming("payment-success-in")
    public void onPaymentSuccess(PaymentSuccessEventPayload paymentSuccessEventPayload) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEventPayload);

        //executor.runAsync(()->{
            //TODO: check if licenses for order exist, else return

            List<OrderItemEntity> orderItemEntities = paymentSuccessEventPayload.getOrderEntity().getOrderItems();

            orderItemEntities.forEach(o->generateLicense(o, paymentSuccessEventPayload.getOrderEntity().getUserId(), paymentSuccessEventPayload.getOrderEntity().getId()));

            List<LicenseEntity> licenses = LicenseEntity.find("orderId", paymentSuccessEventPayload.getOrderEntity().getId()).list();
            //TODO: error handling

            LicensesGeneratedEventPayload licensesGeneratedEventPayload = new LicensesGeneratedEventPayload();
            licensesGeneratedEventPayload.setLicenses(licenses);
            licensesGeneratedEventPayload.setOrderId(paymentSuccessEventPayload.getOrderEntity().getId());
            licensesGeneratedEventPayload.setUserId(paymentSuccessEventPayload.getOrderEntity().getUserId());
            licensesGeneratedEmitter.send(licensesGeneratedEventPayload);
        //});

    }

    private void generateLicense(OrderItemEntity orderItemEntity, UUID userId, UUID orderId) {
        LicenseEntity licenseEntity = new LicenseEntity();
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", orderItemEntity.getProductId()).firstResult();

        //TODO: generate quantity times

        licenseEntity.setProductId(orderItemEntity.getProductId());
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


}
