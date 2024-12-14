package com.hrbatovic.quarkus.master.notification.messaging.consumers;

import com.hrbatovic.quarkus.master.notification.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.notification.mapper.MapUtil;
import com.hrbatovic.quarkus.master.notification.messaging.model.LicensesGeneratedEventPayload;
import com.hrbatovic.quarkus.master.notification.messaging.model.OrderNotificationSentEventPayload;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserRegisteredEvent;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.vertx.ext.auth.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;

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
    Emitter<OrderNotificationSentEventPayload> orderNotificationSentEmitter;

    @Incoming("licenses-generated-in")
    public void onLicensesGenerated(LicensesGeneratedEventPayload licensesGeneratedEventPayload){
        System.out.println("Recieved licenses-generated-in event: " + licensesGeneratedEventPayload);
        executor.runAsync(() -> {
            sendOrderConfirmation(licensesGeneratedEventPayload);
            OrderNotificationSentEventPayload orderNotificationSentEventPayload = new OrderNotificationSentEventPayload();
            orderNotificationSentEventPayload.setOrderId(licensesGeneratedEventPayload.getOrderId());
            orderNotificationSentEmitter.send(orderNotificationSentEventPayload);
        });

    }


    @Incoming("user-registered-in")
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        System.out.println("Recieved user-registered-in event");

        executor.runAsync(() -> {
            if (UserEntity.findById(userRegisteredEvent.getId()) != null) {
                return;
            }
            UserEntity userEntity = mapper.map(userRegisteredEvent);

            userEntity.persist();
        });
    }

    public void sendOrderConfirmation(LicensesGeneratedEventPayload licensesGeneratedEventPayload){
        UserEntity userEntity = UserEntity.findById(licensesGeneratedEventPayload.getUserId());

        mailer.send(Mail.withText(userEntity.getEmail(), "test esad mail", "Hi there! " + licensesGeneratedEventPayload.getLicenses()));
    }


}
