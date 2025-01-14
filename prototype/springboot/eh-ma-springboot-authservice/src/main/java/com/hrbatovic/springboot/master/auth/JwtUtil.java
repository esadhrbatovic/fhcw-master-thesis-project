package com.hrbatovic.springboot.master.auth;

import com.hrbatovic.springboot.master.auth.db.entities.RegistrationEntity;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUtil {
    private static final String SECRET = "8a612b72e7f7159cdc6d09f93397ff56347b71396ff0cd446c1775c991b7654cedf0cbb2a0065e0ba4a8aca66829d0f0bc0a7ae4ffe118284f216e1ed381dbd3";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public JwtTokenContainer buildJwtToken(RegistrationEntity registrationEntity) {
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("iss", "eh-ma-springboot-authservice");
        claims.put("upn", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("sid", UUID.randomUUID().toString());
        claims.put("sub", registrationEntity.getUserEntity().getId().toString());
        claims.put("email", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("given_name", registrationEntity.getUserEntity().getFirstName());
        claims.put("family_name", registrationEntity.getUserEntity().getLastName());

        String jwtTokenAsString = Jwts.builder()
                .subject(registrationEntity.getCredentialsEntity().getEmail())
                .claim("roles", List.of(registrationEntity.getUserEntity().getRole()))
                .addClaims(claims)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return new JwtTokenContainer(jwtTokenAsString, claims);
    }

    public JwtTokenContainer buildJwtTokenKeepSession(RegistrationEntity registrationEntity, UUID sessionId) {
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("iss", "eh-ma-springboot-authservice");
        claims.put("upn", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("sid", sessionId);
        claims.put("sub", registrationEntity.getUserEntity().getId().toString());
        claims.put("email", registrationEntity.getCredentialsEntity().getEmail());
        claims.put("given_name", registrationEntity.getUserEntity().getFirstName());
        claims.put("family_name", registrationEntity.getUserEntity().getLastName());

        String jwtTokenAsString = Jwts.builder()
                .subject(registrationEntity.getCredentialsEntity().getEmail())
                .claim("roles", List.of(registrationEntity.getUserEntity().getRole()))
                .addClaims(claims)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return new JwtTokenContainer(jwtTokenAsString, claims);
    }


}