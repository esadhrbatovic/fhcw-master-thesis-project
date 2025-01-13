package com.hrbatovic.micronaut.master.tracking.api;

import com.hrbatovic.micronaut.master.tracking.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi{

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut trackingservice is up and running.");
    }
}
