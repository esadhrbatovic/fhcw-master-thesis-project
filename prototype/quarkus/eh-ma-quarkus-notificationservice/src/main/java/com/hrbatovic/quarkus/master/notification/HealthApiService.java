package com.hrbatovic.quarkus.master.notification;

import com.hrbatovic.master.quarkus.notification.api.HealthApi;
import com.hrbatovic.master.quarkus.notification.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus notificationservice is up and running.");
    }
}
