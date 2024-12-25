package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.api.HealthApi;
import com.hrbatovic.master.quarkus.user.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {


    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus userservice is up and running.");
    }
}
