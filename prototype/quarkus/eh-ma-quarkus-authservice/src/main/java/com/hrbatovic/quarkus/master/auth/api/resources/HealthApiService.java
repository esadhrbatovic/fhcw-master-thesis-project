package com.hrbatovic.quarkus.master.auth.api.resources;

import com.hrbatovic.master.quarkus.auth.api.HealthApi;
import com.hrbatovic.master.quarkus.auth.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;

import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus authservice is up and running.")).status(200).build();
    }
}
