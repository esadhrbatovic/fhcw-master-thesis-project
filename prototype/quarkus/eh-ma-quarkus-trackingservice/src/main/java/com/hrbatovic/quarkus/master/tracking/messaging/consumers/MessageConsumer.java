package com.hrbatovic.quarkus.master.tracking.messaging.consumers;

import com.hrbatovic.quarkus.master.tracking.db.entities.EventEntity;
import com.hrbatovic.quarkus.master.tracking.db.entities.MetadataEntity;
import com.hrbatovic.quarkus.master.tracking.messaging.model.in.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Incoming("user-updated-in")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("user-updated");
            eventEntity.setBody(userUpdatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(userUpdatedEvent.getUserId());
            metadataEntity.setTimestamp(userUpdatedEvent.getTimestamp());
            metadataEntity.setUserMail(userUpdatedEvent.getUserEmail());
            metadataEntity.setSessionId(userUpdatedEvent.getSessionId());
            metadataEntity.setSourceService(userUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId();
            //metadataEntity.setProductId();
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("user-registered");
            eventEntity.setBody(userRegisteredEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(userRegisteredEvent.getUserId());
            metadataEntity.setTimestamp(userRegisteredEvent.getTimestamp());
            metadataEntity.setUserMail(userRegisteredEvent.getUserEmail());
            metadataEntity.setSessionId(userRegisteredEvent.getSessionId());
            metadataEntity.setSourceService(userRegisteredEvent.getSourceService());
            //metadataEntity.setOrderId();
            //metadataEntity.setProductId();
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("user-deleted-in")
    public void consumeUserDeleted(UUID id) {
        System.out.println("Recieved user-deleted-in event: " + id);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("user-deleted");
            eventEntity.setBody(id.toString());
            //MetadataEntity metadataEntity = new MetadataEntity();
            //metadataEntity.setUserId(userRegisteredEvent.getUserId());
            //metadataEntity.setTimestamp(userRegisteredEvent.getTimestamp());
            //metadataEntity.setUserMail(userRegisteredEvent.getUserEmail());
            //metadataEntity.setSesionId(userRegisteredEvent.getSessionId());
            //metadataEntity.setSourceService(userRegisteredEvent.getSourceService());
            //metadataEntity.setOrderId();
            //metadataEntity.setProductId();
            //eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }


    @Incoming("user-credentials-updated-in")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event: " + userCredentialsUpdatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("user-credentials-updated");
            eventEntity.setBody(userCredentialsUpdatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(userCredentialsUpdatedEvent.getUserId());
            metadataEntity.setTimestamp(userCredentialsUpdatedEvent.getTimestamp());
            metadataEntity.setUserMail(userCredentialsUpdatedEvent.getUserEmail());
            metadataEntity.setSessionId(userCredentialsUpdatedEvent.getSessionId());
            metadataEntity.setSourceService(userCredentialsUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId();
            //metadataEntity.setProductId();
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("product-created-in")
    public void onProductCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Recieved product-created-in event: " + productCreatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("product-created");
            eventEntity.setBody(productCreatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(productCreatedEvent.getUserId());
            metadataEntity.setTimestamp(productCreatedEvent.getTimestamp());
            metadataEntity.setUserMail(productCreatedEvent.getUserEmail());
            metadataEntity.setSessionId(productCreatedEvent.getSessionId());
            metadataEntity.setSourceService(productCreatedEvent.getSourceService());
            //metadataEntity.setOrderId();
            metadataEntity.setProductId(productCreatedEvent.getProduct().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("product-updated-in")
    public void onProductUpdated(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Recieved product-updated-in event: " + productUpdatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("product-updated");
            eventEntity.setBody(productUpdatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(productUpdatedEvent.getUserId());
            metadataEntity.setTimestamp(productUpdatedEvent.getTimestamp());
            metadataEntity.setUserMail(productUpdatedEvent.getUserEmail());
            metadataEntity.setSessionId(productUpdatedEvent.getSessionId());
            metadataEntity.setSourceService(productUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId();
            metadataEntity.setProductId(productUpdatedEvent.getProduct().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("product-deleted-in")
    public void onProductDeleted(UUID id) {
        System.out.println("Recieved product-deleted-in event: " + id);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("product-deleted");
            eventEntity.setBody(id.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            //metadataEntity.setUserId(productUpdatedEvent.getUserId());
            //metadataEntity.setTimestamp(productUpdatedEvent.getTimestamp());
            //metadataEntity.setUserMail(productUpdatedEvent.getUserEmail());
            //metadataEntity.setSesionId(productUpdatedEvent.getSessionId());
            //metadataEntity.setSourceService(productUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId();
            metadataEntity.setProductId(id);
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("checkout-started-in")
    public void onCheckoutStared(CheckoutStartedEvent checkoutStartedEvent) {
        System.out.println("Recieved checkout-started-in event: " + checkoutStartedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("checkout-started");
            eventEntity.setBody(checkoutStartedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(checkoutStartedEvent.getUserId());
            metadataEntity.setTimestamp(checkoutStartedEvent.getTimestamp());
            metadataEntity.setUserMail(checkoutStartedEvent.getUserEmail());
            metadataEntity.setSessionId(checkoutStartedEvent.getSessionId());
            metadataEntity.setSourceService(checkoutStartedEvent.getSourceService());
            //metadataEntity.setOrderId(checkoutStartedEvent.getCart().getId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("order-created-in")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("order-created");
            eventEntity.setBody(orderCreatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(orderCreatedEvent.getUserId());
            metadataEntity.setTimestamp(orderCreatedEvent.getTimestamp());
            metadataEntity.setUserMail(orderCreatedEvent.getUserEmail());
            metadataEntity.setSessionId(orderCreatedEvent.getSessionId());
            metadataEntity.setSourceService(orderCreatedEvent.getSourceService());
            metadataEntity.setOrderId(orderCreatedEvent.getOrder().getId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("payment-success-in")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("payment-success");
            eventEntity.setBody(paymentSuccessEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(paymentSuccessEvent.getUserId());
            metadataEntity.setTimestamp(paymentSuccessEvent.getTimestamp());
            metadataEntity.setUserMail(paymentSuccessEvent.getUserEmail());
            metadataEntity.setSessionId(paymentSuccessEvent.getSessionId());
            metadataEntity.setSourceService(paymentSuccessEvent.getSourceService());
            metadataEntity.setOrderId(paymentSuccessEvent.getPaymentPayload().getOrderId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }


    @Incoming("payment-fail-in")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("payment-fail");
            eventEntity.setBody(paymentFailEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(paymentFailEvent.getUserId());
            metadataEntity.setTimestamp(paymentFailEvent.getTimestamp());
            metadataEntity.setUserMail(paymentFailEvent.getUserEmail());
            metadataEntity.setSessionId(paymentFailEvent.getSessionId());
            metadataEntity.setSourceService(paymentFailEvent.getSourceService());
            metadataEntity.setOrderId(paymentFailEvent.getPaymentPayload().getOrderId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("license-generated-in")
    public void onLicenseGenerated(LicensesGeneratedEvent licensesGeneratedEvent) {
        System.out.println("Recieved license-generated-in event: " + licensesGeneratedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("license-generated");
            eventEntity.setBody(licensesGeneratedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(licensesGeneratedEvent.getUserId());
            metadataEntity.setTimestamp(licensesGeneratedEvent.getTimestamp());
            metadataEntity.setUserMail(licensesGeneratedEvent.getUserEmail());
            metadataEntity.setSessionId(licensesGeneratedEvent.getSessionId());
            metadataEntity.setSourceService(licensesGeneratedEvent.getSourceService());
            metadataEntity.setOrderId(licensesGeneratedEvent.getOrderId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("license-template-created-in")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent) {
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("license-template-created");
            eventEntity.setBody(licenseTemplateCreatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(licenseTemplateCreatedEvent.getUserId());
            metadataEntity.setTimestamp(licenseTemplateCreatedEvent.getTimestamp());
            metadataEntity.setUserMail(licenseTemplateCreatedEvent.getUserEmail());
            metadataEntity.setSessionId(licenseTemplateCreatedEvent.getSessionId());
            metadataEntity.setSourceService(licenseTemplateCreatedEvent.getSourceService());
            //metadataEntity.setOrderId(licenseTemplateCreatedEvent.get());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("license-template-updated-in")
    public void onLicenseTemplateUpdated(LicenseTemplateUpdatedEvent licenseTemplateUpdatedEvent) {
        System.out.println("Recieved license-template-updated-in event: " + licenseTemplateUpdatedEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("license-template-updated");
            eventEntity.setBody(licenseTemplateUpdatedEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(licenseTemplateUpdatedEvent.getUserId());
            metadataEntity.setTimestamp(licenseTemplateUpdatedEvent.getTimestamp());
            metadataEntity.setUserMail(licenseTemplateUpdatedEvent.getUserEmail());
            metadataEntity.setSessionId(licenseTemplateUpdatedEvent.getSessionId());
            metadataEntity.setSourceService(licenseTemplateUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId(licenseTemplateCreatedEvent.get());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("license-template-deleted-in")
    public void onLicenseTemplateDeleted(UUID id) {
        System.out.println("Recieved license-template-deleted-in event: " + id);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("license-template-deleted");
            eventEntity.setBody(id.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            //metadataEntity.setUserId(licenseTemplateUpdatedEvent.getUserId());
            //metadataEntity.setTimestamp(licenseTemplateUpdatedEvent.getTimestamp());
            //metadataEntity.setUserMail(licenseTemplateUpdatedEvent.getUserEmail());
            //metadataEntity.setSesionId(licenseTemplateUpdatedEvent.getSessionId());
            //metadataEntity.setSourceService(licenseTemplateUpdatedEvent.getSourceService());
            //metadataEntity.setOrderId(licenseTemplateCreatedEvent.get());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }

    @Incoming("order-notification-sent-in")
    public void onOrderNotificationSent(OrderNotificationSentEvent orderNotificationSentEvent) {
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEvent);
        executor.runAsync(() -> {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setEventType("order-notification-sent");
            eventEntity.setBody(orderNotificationSentEvent.toString());
            MetadataEntity metadataEntity = new MetadataEntity();
            metadataEntity.setUserId(orderNotificationSentEvent.getUserId());
            metadataEntity.setTimestamp(orderNotificationSentEvent.getTimestamp());
            metadataEntity.setUserMail(orderNotificationSentEvent.getUserEmail());
            metadataEntity.setSessionId(orderNotificationSentEvent.getSessionId());
            metadataEntity.setSourceService(orderNotificationSentEvent.getSourceService());
            metadataEntity.setOrderId(orderNotificationSentEvent.getOrderId());
            //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
            eventEntity.setMetadata(metadataEntity);
            eventEntity.persist();
        });
    }
}
