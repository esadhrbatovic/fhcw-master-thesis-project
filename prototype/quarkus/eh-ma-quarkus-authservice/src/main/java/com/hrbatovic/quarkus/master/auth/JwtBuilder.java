package com.hrbatovic.quarkus.master.auth;

import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.util.HashSet;

@ApplicationScoped
public class JwtBuilder {

    @Inject
    @ConfigProperty(name = "jwt.issuer")
    String jwtIssuer;

    public String buildJwtToken(RegistrationEntity registrationEntity) {
        HashSet<String> groups = new HashSet<>();
        groups.add(registrationEntity.getUserEntity().getRole());

        return Jwt
                .issuer(jwtIssuer)
                .upn(registrationEntity.getCredentialsEntity().getEmail())
                .groups(groups)
                .claim(Claims.sub, registrationEntity.getUserEntity().id)
                .claim(Claims.email, registrationEntity.getCredentialsEntity().getEmail())
                .claim(Claims.given_name, registrationEntity.getUserEntity().getFirstName())
                .claim(Claims.family_name, registrationEntity.getUserEntity().getLastName())
                .expiresAt(Instant.now().plusSeconds(3600)).sign();
    }
}