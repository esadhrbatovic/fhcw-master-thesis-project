package com.hrbatovic.micronaut.master.payment;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import io.micronaut.security.utils.SecurityService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Singleton
public class JwtUtil {

    @Inject
    private SecurityService securityService;

    public String getClaimFromSecurityContext(String claimName){
        return securityService.getAuthentication()
                .map(auth -> auth.getAttributes().get(claimName))
                .map(Object::toString)
                .orElse(null);
    }

    public List<String> getRoles() {
        return securityService.getAuthentication()
                .map(auth -> auth.getAttributes().get("roles"))
                .filter(Objects::nonNull)
                .map(roles -> (List<String>) roles)
                .orElse(Collections.emptyList());
    }
}
