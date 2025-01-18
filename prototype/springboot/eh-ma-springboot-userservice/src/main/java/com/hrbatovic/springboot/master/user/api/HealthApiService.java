package com.hrbatovic.springboot.master.user.api;

import com.hrbatovic.master.springboot.user.api.HealthApi;
import com.hrbatovic.master.springboot.user.model.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

	@Override
	public SuccessResponse healthCheck() {
		return new SuccessResponse().message("Springboot userservice is up and running.");
	}
}
