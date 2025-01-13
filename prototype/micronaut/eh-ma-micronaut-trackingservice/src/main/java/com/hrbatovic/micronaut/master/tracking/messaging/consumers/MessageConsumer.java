package com.hrbatovic.micronaut.master.tracking.messaging.consumers;

import com.hrbatovic.micronaut.master.tracking.db.entities.EventEntity;
import com.hrbatovic.micronaut.master.tracking.db.entities.MetadataEntity;
import com.hrbatovic.micronaut.master.tracking.db.repositories.EventRepository;
import com.hrbatovic.micronaut.master.tracking.messaging.model.in.*;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import java.util.UUID;

@KafkaListener(groupId = "tracking-events-group")
public class MessageConsumer {

    @Inject
    EventRepository eventRepository;

    @Topic("user-registered")
    public void onUserUpdated(UserUpdatedEvent userUpdatedEvent){
        System.out.println("Recieved user-updated-in event: " + userUpdatedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("user-updated");
        eventEntity.setBody(userUpdatedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(userUpdatedEvent.getUserId());
        metadataEntity.setTimestamp(userUpdatedEvent.getTimestamp());
        metadataEntity.setUserMail(userUpdatedEvent.getUserEmail());
        metadataEntity.setSessionId(userUpdatedEvent.getSessionId());
        metadataEntity.setSourceService(userUpdatedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(userUpdatedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId();
        //metadataEntity.setProductId();
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }

    @Topic("user-registered")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent){
        System.out.println("Recieved user-registered-in event: " + userRegisteredEvent);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("user-registered");
        eventEntity.setBody(userRegisteredEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(userRegisteredEvent.getUserId());
        metadataEntity.setTimestamp(userRegisteredEvent.getTimestamp());
        metadataEntity.setUserMail(userRegisteredEvent.getUserEmail());
        metadataEntity.setSessionId(userRegisteredEvent.getSessionId());
        metadataEntity.setSourceService(userRegisteredEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(userRegisteredEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId();
        //metadataEntity.setProductId();
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("user-deleted")
    public void consumeUserDeleted(UUID id) {
        System.out.println("Recieved user-deleted-in event: " + id);
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
        eventRepository.save(eventEntity);
    }


    @Topic("user-credentials-updated")
    public void onUserCredentialsUpdated(UserCredentialsUpdatedEvent userCredentialsUpdatedEvent) {
        System.out.println("Recieved user-credentials-updated-in event: " + userCredentialsUpdatedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("user-credentials-updated");
        eventEntity.setBody(userCredentialsUpdatedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(userCredentialsUpdatedEvent.getUserId());
        metadataEntity.setTimestamp(userCredentialsUpdatedEvent.getTimestamp());
        metadataEntity.setUserMail(userCredentialsUpdatedEvent.getUserEmail());
        metadataEntity.setSessionId(userCredentialsUpdatedEvent.getSessionId());
        metadataEntity.setSourceService(userCredentialsUpdatedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(userCredentialsUpdatedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId();
        //metadataEntity.setProductId();
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("product-created")
    public void onProductCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Recieved product-created-in event: " + productCreatedEvent);
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
        metadataEntity.setRequestCorrelationId(productCreatedEvent.getRequestCorrelationId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("product-updated")
    public void onProductUpdated(ProductUpdatedEvent productUpdatedEvent) {
        System.out.println("Recieved product-updated-in event: " + productUpdatedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("product-updated");
        eventEntity.setBody(productUpdatedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(productUpdatedEvent.getUserId());
        metadataEntity.setTimestamp(productUpdatedEvent.getTimestamp());
        metadataEntity.setUserMail(productUpdatedEvent.getUserEmail());
        metadataEntity.setSessionId(productUpdatedEvent.getSessionId());
        metadataEntity.setSourceService(productUpdatedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(productUpdatedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId();
        metadataEntity.setProductId(productUpdatedEvent.getProduct().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("product-deleted")
    public void onProductDeleted(UUID id) {
        System.out.println("Recieved product-deleted-in event: " + id);
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
        eventRepository.save(eventEntity);
    }

    @Topic("checkout-started")
    public void onCheckoutStared(CheckoutStartedEvent checkoutStartedEvent) {
        System.out.println("Recieved checkout-started-in event: " + checkoutStartedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("checkout-started");
        eventEntity.setBody(checkoutStartedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(checkoutStartedEvent.getUserId());
        metadataEntity.setTimestamp(checkoutStartedEvent.getTimestamp());
        metadataEntity.setUserMail(checkoutStartedEvent.getUserEmail());
        metadataEntity.setSessionId(checkoutStartedEvent.getSessionId());
        metadataEntity.setSourceService(checkoutStartedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(checkoutStartedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId(checkoutStartedEvent.getCart().getId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("order-created")
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);
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
        metadataEntity.setRequestCorrelationId(orderCreatedEvent.getRequestCorrelationId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("payment-success")
    public void onPaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        System.out.println("Recieved payment-success-in event: " + paymentSuccessEvent);
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
        metadataEntity.setRequestCorrelationId(paymentSuccessEvent.getRequestCorrelationId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("payment-fail")
    public void onPaymentFail(PaymentFailEvent paymentFailEvent) {
        System.out.println("Recieved payment-fail-in event: " + paymentFailEvent);
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
        metadataEntity.setRequestCorrelationId(paymentFailEvent.getRequestCorrelationId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("license-generated")
    public void onLicenseGenerated(LicensesGeneratedEvent licensesGeneratedEvent) {
        System.out.println("Recieved license-generated-in event: " + licensesGeneratedEvent);
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
        metadataEntity.setRequestCorrelationId(licensesGeneratedEvent.getRequestCorrelationId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("license-template-created")
    public void onLicenseTemplateCreated(LicenseTemplateCreatedEvent licenseTemplateCreatedEvent) {
        System.out.println("Recieved license-template-created-in event: " + licenseTemplateCreatedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("license-template-created");
        eventEntity.setBody(licenseTemplateCreatedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(licenseTemplateCreatedEvent.getUserId());
        metadataEntity.setTimestamp(licenseTemplateCreatedEvent.getTimestamp());
        metadataEntity.setUserMail(licenseTemplateCreatedEvent.getUserEmail());
        metadataEntity.setSessionId(licenseTemplateCreatedEvent.getSessionId());
        metadataEntity.setSourceService(licenseTemplateCreatedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(licenseTemplateCreatedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId(licenseTemplateCreatedEvent.get());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }

    @Topic("license-template-updated")
    public void onLicenseTemplateUpdated(LicenseTemplateUpdatedEvent licenseTemplateUpdatedEvent) {
        System.out.println("Recieved license-template-updated-in event: " + licenseTemplateUpdatedEvent);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType("license-template-updated");
        eventEntity.setBody(licenseTemplateUpdatedEvent.toString());
        MetadataEntity metadataEntity = new MetadataEntity();
        metadataEntity.setUserId(licenseTemplateUpdatedEvent.getUserId());
        metadataEntity.setTimestamp(licenseTemplateUpdatedEvent.getTimestamp());
        metadataEntity.setUserMail(licenseTemplateUpdatedEvent.getUserEmail());
        metadataEntity.setSessionId(licenseTemplateUpdatedEvent.getSessionId());
        metadataEntity.setSourceService(licenseTemplateUpdatedEvent.getSourceService());
        metadataEntity.setRequestCorrelationId(licenseTemplateUpdatedEvent.getRequestCorrelationId());
        //metadataEntity.setOrderId(licenseTemplateCreatedEvent.get());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }


    @Topic("license-template-deleted")
    public void onLicenseTemplateDeleted(UUID id) {
        System.out.println("Recieved license-template-deleted-in event: " + id);
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
        eventRepository.save(eventEntity);
    }

    @Topic("order-notification-sent")
    public void onOrderNotificationSent(OrderNotificationSentEvent orderNotificationSentEvent) {
        System.out.println("Recieved order-notification-sent-in event: " + orderNotificationSentEvent);
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
        metadataEntity.setRequestCorrelationId(orderNotificationSentEvent.getRequestCorrelationId());
        //metadataEntity.setProductId(checkoutStartedEvent.getCart().getId());
        eventEntity.setMetadata(metadataEntity);
        eventRepository.save(eventEntity);
    }

}
