package com.hrbatovic.micronaut.master.cart.api;

import com.hrbatovic.micronaut.master.cart.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi{

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut cartservice is up and running.");
    }
}
