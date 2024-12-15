package com.hrbatovic.master.quarkus.license;

import com.hrbatovic.master.quarkus.license.api.HealthApi;
import com.hrbatovic.master.quarkus.license.model.SuccessResponse;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Quarkus licenseservice is up and running.");
    }
}
