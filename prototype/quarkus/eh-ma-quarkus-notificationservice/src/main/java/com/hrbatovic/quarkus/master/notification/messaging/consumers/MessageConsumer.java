package com.hrbatovic.quarkus.master.notification.messaging.consumers;

import com.hrbatovic.quarkus.master.notification.messaging.model.LicensesGeneratedEventPayload;
import com.hrbatovic.quarkus.master.notification.messaging.model.OrderNotificationSentEventPayload;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    Mailer mailer;

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


    public void sendOrderConfirmation(LicensesGeneratedEventPayload licensesGeneratedEventPayload){
        mailer.send(Mail.withText("esadh@live.de", "test esad mail", "Hi there! " + licensesGeneratedEventPayload.getLicenses()));
    }


}
