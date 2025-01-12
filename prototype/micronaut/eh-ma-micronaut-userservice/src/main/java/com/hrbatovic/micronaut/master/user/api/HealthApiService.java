package com.hrbatovic.micronaut.master.user.api;

import com.hrbatovic.micronaut.master.user.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Singleton
@Controller
public class HealthApiService implements HealthApi{


    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut userservice is up and running.");
    }
}