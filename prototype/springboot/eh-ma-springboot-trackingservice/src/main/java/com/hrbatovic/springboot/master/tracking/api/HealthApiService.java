package com.hrbatovic.springboot.master.tracking.api;

import com.hrbatovic.master.springboot.tracking.api.HealthApi;
import com.hrbatovic.master.springboot.tracking.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Springboot trackingservice is up and running.");
    }
}
