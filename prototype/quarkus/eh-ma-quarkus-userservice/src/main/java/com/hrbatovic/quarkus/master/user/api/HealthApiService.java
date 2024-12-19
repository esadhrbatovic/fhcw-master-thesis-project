package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.api.HealthApi;
import com.hrbatovic.master.quarkus.user.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {


    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus userservice is up and running.")).status(200).build();
    }
}
