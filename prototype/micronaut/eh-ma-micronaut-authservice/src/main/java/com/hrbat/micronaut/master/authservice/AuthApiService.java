package com.hrbat.micronaut.master.authservice;

import com.hrbat.master.micronaut.api.AuthenticationApi;
import com.hrbat.master.micronaut.model.LoginRequest;
import com.hrbat.master.micronaut.model.LoginResponse;
import com.hrbat.master.micronaut.model.RegisterRequest;
import com.hrbat.master.micronaut.model.RegisterResponse;
import com.hrbat.micronaut.master.authservice.db.model.RegistrationEntity;
import com.hrbat.micronaut.master.authservice.db.repositories.RegistrationRepository;
import com.hrbat.micronaut.master.authservice.mapper.MapUtil;
import com.hrbat.micronaut.master.authservice.messaging.model.out.UserRegisteredEvent;
import io.micronaut.http.annotation.Controller;
import io.micronaut.openapi.visitor.security.SecurityRule;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.token.generator.TokenGenerator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class AuthApiService implements AuthenticationApi {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_CUSTOMER = "customer";

    @Inject
    TokenGenerator tokenGenerator;

    private PasswordEncoder passwordEncoder;

    @Inject
    RegistrationRepository registrationRepository;

    @Inject
    UserRegisteredProducer userRegisteredProducer;

    @Override
    @Secured(SecurityRule.IS_ANONYMOUS)
    public LoginResponse login(LoginRequest loginRequest) {

        RegistrationEntity registrationEntity = registrationRepository.findByCredentialsEntityEmail(loginRequest.getCredentials().getEmail()).orElse(null);

        if (registrationEntity == null) {
            throw new RuntimeException("Login Failed");
        }

        passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())){
            throw new RuntimeException("Login Failed");
        }

        HashMap<String, Object> claims = new HashMap<>();

        long now = System.currentTimeMillis() / 1000;
        long expiry = now + 3600;

        claims.put("iss", "eh-ma-micronaut-authservice");
        claims.put("sub", registrationEntity.getUserEntity().getId());
        claims.put("nbf", now);
        claims.put("exp", expiry);
        claims.put("iat", now);
        claims.put("roles", List.of(registrationEntity.getUserEntity().getRole()));

        String jwtToken = tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));


        return new LoginResponse().message("Login successful").token(jwtToken);
    }

    @Override
    @Secured(SecurityRule.IS_ANONYMOUS)
    public RegisterResponse register(RegisterRequest registerRequest) {
        passwordEncoder = new BCryptPasswordEncoder(10);
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUserEntity(MapUtil.INSTANCE.map(registerRequest.getUserData()));

        registrationEntity.setCredentialsEntity(MapUtil.INSTANCE.map(registerRequest.getCredentials()));

        registrationEntity.getCredentialsEntity().setPassword(passwordEncoder.encode(registerRequest.getCredentials().getPassword()));


        if (registrationRepository.count() == 0) {
            registrationEntity.getUserEntity().setRole(ROLE_ADMIN);
        } else {
            registrationEntity.getUserEntity().setRole(ROLE_CUSTOMER);
        }


        registrationRepository.save(registrationEntity);
        HashMap<String, Object> claims = new HashMap<>();


        long now = System.currentTimeMillis() / 1000;
        long expiry = now + 3600;

        claims.put("iss", "eh-ma-micronaut-authservice");
        claims.put("sub", registrationEntity.getUserEntity().getId());
        claims.put("nbf", now);
        claims.put("exp", expiry);
        claims.put("iat", now);
        claims.put("roles", List.of(registrationEntity.getUserEntity().getRole()));

        String jwtToken = tokenGenerator.generateToken(claims)
                .orElseThrow(() -> new RuntimeException("Failed to generate token"));

        //TODO: define session id
        UserRegisteredEvent userRegisteredEvent = buildUserRegisteredEvent(registerRequest, registrationEntity, UUID.randomUUID(), registrationEntity.getCredentialsEntity().getEmail());
        userRegisteredProducer.sendMessage(userRegisteredEvent);
        return new RegisterResponse()
                .message("User registered successfully")
                .token(jwtToken);
    }


    private UserRegisteredEvent buildUserRegisteredEvent(RegisterRequest registerRequest, RegistrationEntity registrationEntity, UUID sessionId, String userEmail) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent().setUserPayload(MapUtil.INSTANCE.map(registerRequest));
        userRegisteredEvent.getUserPayload().setRole(registrationEntity.getUserEntity().getRole());
        userRegisteredEvent.getUserPayload().setId(registrationEntity.getUserEntity().getId());
        userRegisteredEvent.setTimestamp(LocalDateTime.now());
        userRegisteredEvent.setUserId(registrationEntity.getUserEntity().getId());
        userRegisteredEvent.setUserEmail(userEmail);
        userRegisteredEvent.setSessionId(sessionId);
        userRegisteredEvent.setRequestCorrelationId(UUID.randomUUID());
        return userRegisteredEvent;
    }
}
