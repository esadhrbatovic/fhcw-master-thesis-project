package com.hrbatovic.micronaut.master.payment.api;

import com.hrbatovic.micronaut.master.payment.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi {
    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut paymentservice is up and running.");
    }
}
