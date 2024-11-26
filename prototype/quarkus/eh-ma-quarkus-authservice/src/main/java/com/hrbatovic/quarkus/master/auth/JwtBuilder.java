package com.hrbatovic.quarkus.master.auth;

import com.hrbatovic.quarkus.master.auth.db.entities.UserEntity;
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

    public String buildJwtToken(UserEntity userEntity) {
        HashSet<String> groups = new HashSet<>();
        groups.add(userEntity.getRole());

        return Jwt
                .issuer(jwtIssuer)
                .upn(userEntity.getEmail())
                .groups(groups)
                .claim(Claims.sub, userEntity.id)
                .claim(Claims.email, userEntity.getEmail())
                .claim(Claims.given_name, userEntity.getFirstName())
                .claim(Claims.family_name, userEntity.getLastName())
                .expiresAt(Instant.now().plusSeconds(3600)).sign();
    }
}