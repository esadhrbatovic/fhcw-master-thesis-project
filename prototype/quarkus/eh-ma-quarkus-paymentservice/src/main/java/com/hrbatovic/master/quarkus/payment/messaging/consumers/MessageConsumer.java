package com.hrbatovic.master.quarkus.payment.messaging.consumers;

import com.hrbatovic.master.quarkus.payment.messaging.model.OrderCreatedEventPayload;
import com.hrbatovic.master.quarkus.payment.messaging.model.PaymentSuccessEventPayload;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Inject
    @Channel("payment-success-out")
    Emitter<PaymentSuccessEventPayload> paymentSuccessEmitter;

    @Incoming("order-created-in")
    public void onOrderCreated(OrderCreatedEventPayload orderCreatedEvent) {
        System.out.println("Recieved order-created-in event: " + orderCreatedEvent);

        executor.runAsync(()->{
            //TODO: validate payment method
            //TODO: fake check payment status
            PaymentSuccessEventPayload paymentSuccessEventPayload = new PaymentSuccessEventPayload();

            paymentSuccessEventPayload.setOrderEntity(orderCreatedEvent.getOrderEntity());
            paymentSuccessEmitter.send(paymentSuccessEventPayload);
        });
    }



}
