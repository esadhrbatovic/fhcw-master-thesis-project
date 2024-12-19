package com.hrbatovic.quarkus.master.notification.api;

import com.hrbatovic.master.quarkus.notification.api.HealthApi;
import com.hrbatovic.master.quarkus.notification.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public Response healthCheck() {
        return Response.ok(new SuccessResponse().message("Quarkus notificationservice is up and running.")).status(200).build();
    }
}
