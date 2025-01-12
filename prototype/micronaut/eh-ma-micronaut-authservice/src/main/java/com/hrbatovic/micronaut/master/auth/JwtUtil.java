package com.hrbatovic.micronaut.master.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public String buildJwtToken(RegistrationEntity registrationEntity) {
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


        return tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));
    }

    public String buildJwtTokenKeepSession(RegistrationEntity registrationEntity, UUID sessionId) {
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


        return tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));
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
