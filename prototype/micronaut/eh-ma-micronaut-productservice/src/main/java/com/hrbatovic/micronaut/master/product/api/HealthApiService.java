package com.hrbatovic.micronaut.master.product.api;

import com.hrbatovic.micronaut.master.product.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Singleton;

@Singleton
@Controller
public class HealthApiService implements HealthApi{

    @Override
    public SuccessResponse healthCheck() {
        return null;
    }
}
