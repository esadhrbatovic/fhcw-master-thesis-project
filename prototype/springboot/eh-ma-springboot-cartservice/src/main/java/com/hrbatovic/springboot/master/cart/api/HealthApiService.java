package com.hrbatovic.springboot.master.cart.api;

import com.hrbatovic.master.springboot.cart.api.HealthApi;
import com.hrbatovic.master.springboot.cart.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

	@Override
	public SuccessResponse healthCheck() {
		return new SuccessResponse().message("Springboot cartservice is up and running.");
	}
}
