package com.hrbatovic.springboot.master.product.api;

import com.hrbatovic.master.springboot.product.api.HealthApi;
import com.hrbatovic.master.springboot.product.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        return new SuccessResponse().message("Springboot product-service is up and running!");
    }
}
