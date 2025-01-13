package com.hrbatovic.micronaut.master.notification.api;

import com.hrbatovic.micronaut.master.notification.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut notificationservice is up and running.");
    }
}
