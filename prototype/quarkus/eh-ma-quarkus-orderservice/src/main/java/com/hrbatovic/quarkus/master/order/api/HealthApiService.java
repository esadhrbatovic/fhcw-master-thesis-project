package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.HealthApi;
import com.hrbatovic.master.quarkus.order.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus orderservice is up and running.")).status(200).build();
    }
}
