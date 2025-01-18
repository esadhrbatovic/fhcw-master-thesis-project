package com.hrbatovic.springboot.master.payment.api;

import com.hrbatovic.master.springboot.order.api.HealthApi;
import com.hrbatovic.master.springboot.order.model.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

	@Override
	public SuccessResponse healthCheck() {
		return new SuccessResponse().message("Springboot paymentservice is up and running.");
	}
}
