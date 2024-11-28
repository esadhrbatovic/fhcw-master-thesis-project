package com.hrbatovic.quarkus.master.cart.messaging.consumers;

import com.hrbatovic.quarkus.master.cart.db.entities.CartEntity;
import com.hrbatovic.quarkus.master.cart.db.entities.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

@ApplicationScoped
public class MessageConsumer {

    @Inject
    ManagedExecutor executor;

    @Incoming("product-created-in")
    public void onProductCreated(ProductEntity productEntity) {
        System.out.println("Recieved product-created-in event: " + productEntity);

        executor.runAsync(()->{
            if(ProductEntity.findById(productEntity.getId()) != null){
                return;
            }

            productEntity.persistOrUpdate();
        });
    }

    @Incoming("product-updated-in")
    public void onProductUpdated(ProductEntity productEntity) {
        System.out.println("Recieved product-updated-in event: " + productEntity);

        executor.runAsync(()->{
            ProductEntity pE = ProductEntity.findById(productEntity.getId());
            if (pE == null) {
                return;
            }

            productEntity.persistOrUpdate();
        });
    }

    @Incoming("product-deleted-in")
    public void onProductDeleted(UUID id) {
        System.out.println("Recieved product-deleted-in event: " + id);

        executor.runAsync(()->{
            ProductEntity pE = ProductEntity.findById(id);
            if (pE == null) {
                return;
            }

            pE.delete();
        });
    }

    @Incoming("order-created-in")
    public void onOrderCreated(UUID orderId) {
        System.out.println("Recieved order-created-in event: " + orderId);

        executor.runAsync(()->{
            CartEntity cartEntity = CartEntity.findById(orderId);
            if(cartEntity == null){
                return;
            }

            cartEntity.delete();
        });
    }

}
