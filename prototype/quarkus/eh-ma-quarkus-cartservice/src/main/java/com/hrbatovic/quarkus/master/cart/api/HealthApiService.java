package com.hrbatovic.quarkus.master.cart.api;

import com.hrbatovic.master.quarkus.cart.api.HealthApi;
import com.hrbatovic.master.quarkus.cart.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus cartservice is up and running.")).status(200).build();
    }
}
