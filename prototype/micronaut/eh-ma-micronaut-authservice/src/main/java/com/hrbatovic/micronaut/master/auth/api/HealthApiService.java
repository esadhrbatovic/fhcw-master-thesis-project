package com.hrbatovic.micronaut.master.auth.api;

import com.hrbatovic.micronaut.master.auth.api.HealthApi;
import com.hrbatovic.micronaut.master.auth.model.SuccessResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Controller
@Singleton
public class HealthApiService implements HealthApi {

    @Inject
    private SecurityService securityService;

    @Override
    @RolesAllowed({"admin"})
    //@Secured(SecurityRule.IS_ANONYMOUS)
    public SuccessResponse healthCheck() {
        boolean isAuth = securityService.isAuthenticated();

        System.out.println(isAuth);

        String userSub = securityService.getAuthentication()
                .map(auth -> auth.getAttributes().get("sub"))
                .map(Object::toString)
                .orElse(null);

        System.out.println(userSub);
        return new SuccessResponse().message("Micronaut authservice is up and running");
    }
}