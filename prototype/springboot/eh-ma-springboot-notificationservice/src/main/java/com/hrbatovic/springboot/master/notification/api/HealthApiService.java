package com.hrbatovic.springboot.master.notification.api;

import com.hrbatovic.master.springboot.notification.api.HealthApi;
import com.hrbatovic.master.springboot.notification.model.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

	@Override
	public SuccessResponse healthCheck() {
		return new SuccessResponse().message("Springboot notificationservice is up and running.");
	}
}
