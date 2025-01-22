package com.hrbatovic.micronaut.master.notification.api;

import com.hrbatovic.micronaut.master.notification.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi {


    @Override
    @Secured(SecurityRule.IS_ANONYMOUS)
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut notificationservice is up and running.");
    }
}
