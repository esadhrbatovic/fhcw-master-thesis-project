package com.hrbatovic.springboot.master.apigateway.api;


import com.hrbatovic.master.springboot.gateway.api.HealthApi;
import com.hrbatovic.master.springboot.gateway.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

	@Override
	public SuccessResponse healthCheck() {
		return new SuccessResponse().message("Springboot openapigateway is up and running");
	}
}
