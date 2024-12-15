package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.HealthApi;
import com.hrbatovic.master.quarkus.cart.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus cartservice is up and running.");
    }
}
