package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi{

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus paymentservice is up and running.")).status(200).build();
    }
}
