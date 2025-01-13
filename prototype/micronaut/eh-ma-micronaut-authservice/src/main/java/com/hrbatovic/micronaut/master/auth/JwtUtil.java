package com.hrbatovic.micronaut.master.auth;

import com.hrbatovic.micronaut.master.auth.db.entities.RegistrationEntity;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class JwtUtil {

    @Inject
    private TokenGenerator tokenGenerator;

    @Inject
    private SecurityService securityService;


    public JwtTokenContainer buildJwtToken(RegistrationEntity registrationEntity) {
        HashMap<String, Object> claims = new HashMap<>();

        long now = System.currentTimeMillis() / 1000;
        long expiry = now + 3600;

        claims.put("iss", "eh-ma-micronaut-authservice");
        claims.put("upn", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("roles", List.of(registrationEntity.getUserEntity().getRole()));
        claims.put("sid", UUID.randomUUID());
        claims.put("sub", registrationEntity.getUserEntity().getId());
        claims.put("email", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("given_name", registrationEntity.getUserEntity().getFirstName());
        claims.put("family_name", registrationEntity.getUserEntity().getLastName());
        claims.put("nbf", now);
        claims.put("exp", expiry);
        claims.put("iat", now);

        String jwtTokenAsString = tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));
        return new JwtTokenContainer(jwtTokenAsString, claims);
    }

    public JwtTokenContainer buildJwtTokenKeepSession(RegistrationEntity registrationEntity, UUID sessionId) {
        HashMap<String, Object> claims = new HashMap<>();

        long now = System.currentTimeMillis() / 1000;
        long expiry = now + 3600;

        claims.put("iss", "eh-ma-micronaut-authservice");
        claims.put("upn", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("roles", List.of(registrationEntity.getUserEntity().getRole()));
        claims.put("sid", sessionId);
        claims.put("sub", registrationEntity.getUserEntity().getId());
        claims.put("email", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("given_name", registrationEntity.getUserEntity().getFirstName());
        claims.put("family_name", registrationEntity.getUserEntity().getLastName());
        claims.put("nbf", now);
        claims.put("exp", expiry);
        claims.put("iat", now);


        String jwtTokenAsString = tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));
        return new JwtTokenContainer(jwtTokenAsString, claims);
    }

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
