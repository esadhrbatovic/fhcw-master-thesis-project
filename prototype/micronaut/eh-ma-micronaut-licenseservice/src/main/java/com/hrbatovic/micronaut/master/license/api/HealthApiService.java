package com.hrbatovic.micronaut.master.license.api;

import com.hrbatovic.micronaut.master.license.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi {
    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut licenseservice is up and running.");
    }
}
