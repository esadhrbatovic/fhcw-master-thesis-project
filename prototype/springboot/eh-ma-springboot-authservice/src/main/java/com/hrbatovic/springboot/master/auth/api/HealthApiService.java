package com.hrbatovic.springboot.master.auth.api;

import com.hrbatovic.springboot.master.auth.JwtAuthentication;
import com.hrbatovic.springboot.master.auth.model.SuccessResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApiService implements HealthApi {

    @Override
    public SuccessResponse healthCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthentication) {
            JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
            var roles = jwtAuth.getAuthorities(); // Roles
        }

        return new SuccessResponse().message("Springboot authservice is up and running.");
    }
}
