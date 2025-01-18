package com.hrbatovic.springboot.master.license.api;

import com.hrbatovic.master.springboot.license.api.HealthApi;
import com.hrbatovic.master.springboot.license.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Springboot licenseservice is up and running.");
    }
}
