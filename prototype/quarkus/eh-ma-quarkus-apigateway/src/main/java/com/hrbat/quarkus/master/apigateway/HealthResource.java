package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.HealthApi;
import com.hrbat.quarkus.master.apigateway.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@RequestScoped
public class HealthResource implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus apigateway is up and running!");
    }
}
