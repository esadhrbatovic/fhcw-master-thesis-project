package com.hrbatovic.quarkus.master.tracking.api;

import com.hrbatovic.master.quarkus.tracking.api.HealthApi;
import com.hrbatovic.master.quarkus.tracking.model.SuccessResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class HealthResource implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus trackingservice is up and running.")).status(200).build();
    }
}
