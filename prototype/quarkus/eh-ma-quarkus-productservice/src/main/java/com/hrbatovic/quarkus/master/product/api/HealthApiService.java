package com.hrbatovic.quarkus.master.product.api;

import com.hrbatovic.master.quarkus.product.api.HealthApi;
import com.hrbatovic.master.quarkus.product.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus productservice is up and running.");
    }
}
