package com.hrbatovic.springboot.master.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService {

	@GetMapping("/health")

	public String getHealth() {
		return "Spring-Boot is up and running!";
	}
}
