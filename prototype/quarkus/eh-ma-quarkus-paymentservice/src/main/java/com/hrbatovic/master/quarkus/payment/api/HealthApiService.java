package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi{

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus paymentservice is up and running.");
    }
}
