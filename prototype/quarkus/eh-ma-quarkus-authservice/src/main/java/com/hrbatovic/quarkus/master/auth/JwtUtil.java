package com.hrbatovic.quarkus.master.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;
import java.util.UUID;

@ApplicationScoped
public class JwtUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
                .claim("sid", UUID.randomUUID()) //sessionId
                .claim(Claims.sub, registrationEntity.getUserEntity().id)
                .claim(Claims.email, registrationEntity.getCredentialsEntity().getEmail())
                .claim(Claims.given_name, registrationEntity.getUserEntity().getFirstName())
                .claim(Claims.family_name, registrationEntity.getUserEntity().getLastName())
                .expiresAt(Instant.now().plusSeconds(3600)).sign();
    }

    public String buildJwtTokenKeepSession(RegistrationEntity registrationEntity, UUID sessionId) {
        HashSet<String> groups = new HashSet<>();
        groups.add(registrationEntity.getUserEntity().getRole());

        return Jwt
                .issuer(jwtIssuer)
                .upn(registrationEntity.getCredentialsEntity().getEmail())
                .groups(groups)
                .claim("sid", sessionId) //sessionId
                .claim(Claims.sub, registrationEntity.getUserEntity().id)
                .claim(Claims.email, registrationEntity.getCredentialsEntity().getEmail())
                .claim(Claims.given_name, registrationEntity.getUserEntity().getFirstName())
                .claim(Claims.family_name, registrationEntity.getUserEntity().getLastName())
                .expiresAt(Instant.now().plusSeconds(3600)).sign();
    }

    public String extractClaim(String token, String claimName) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                System.err.println("Invalid JWT format.");
                return null;
            }

            String payload = parts[1];

            byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
            String decodedPayload = new String(decodedBytes, "UTF-8");

            JsonNode jsonNode = objectMapper.readTree(decodedPayload);

            JsonNode claimNode = jsonNode.get(claimName);
            if (claimNode != null) {
                return claimNode.asText();
            } else {
                System.err.println("Claim '" + claimName + "' not found in the token.");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error extracting claim: " + e.getMessage());
            return null;
        }
    }


}