package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.HealthApi;
import com.hrbatovic.micronaut.master.apigateway.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Singleton;

@Controller
@Singleton
public class HealthApiService implements HealthApi {

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Micronaut apigateway is up and running!");
    }
}
