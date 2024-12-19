package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.api.HealthApi;
import com.hrbatovic.master.quarkus.license.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus licenseservice is up and running.")).status(200).build();
    }
}
