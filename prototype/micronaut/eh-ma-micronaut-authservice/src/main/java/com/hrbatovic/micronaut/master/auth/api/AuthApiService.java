package com.hrbatovic.micronaut.master.auth.api;

import com.hrbatovic.micronaut.master.auth.Hasher;
import com.hrbatovic.micronaut.master.auth.JwtTokenContainer;
import com.hrbatovic.micronaut.master.auth.JwtUtil;
import com.hrbatovic.micronaut.master.auth.exceptions.EhMaException;
import com.hrbatovic.micronaut.master.auth.messaging.model.out.UserCredentialsUpdatedEvent;
import com.hrbatovic.micronaut.master.auth.messaging.producers.UserCredentialsUpdatedProducer;
import com.hrbatovic.micronaut.master.auth.messaging.producers.UserRegisteredProducer;
import com.hrbatovic.micronaut.master.auth.model.*;
import com.hrbatovic.micronaut.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.micronaut.master.auth.db.repositories.RegistrationRepository;
import com.hrbatovic.micronaut.master.auth.mapper.MapUtil;
import com.hrbatovic.micronaut.master.auth.messaging.model.out.UserRegisteredEvent;
import io.micronaut.http.annotation.Controller;
import io.micronaut.openapi.visitor.security.SecurityRule;
import io.micronaut.security.annotation.Secured;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Singleton
public class AuthApiService implements AuthenticationApi, CredentialsApi {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_CUSTOMER = "customer";

    @Inject
    JwtUtil jwtUtil;

    @Inject
    Hasher hasher;

    @Inject
    MapUtil mapper;

    @Inject
    RegistrationRepository registrationRepository;

    @Inject
    UserRegisteredProducer userRegisteredProducer;

    @Inject
    UserCredentialsUpdatedProducer userCredentialsUpdatedProducer;

    @Override
    @Secured(SecurityRule.IS_ANONYMOUS)
    public LoginResponse login(LoginRequest loginRequest) {

        RegistrationEntity registrationEntity = registrationRepository.findByCredentialsEntityEmail(loginRequest.getCredentials().getEmail()).orElse(null);

        if (registrationEntity == null) {
            throw new EhMaException(400, "Login Failed");
        }

        if(!hasher.check(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())){
            throw new EhMaException(400, "Login Failed");
        }

        JwtTokenContainer jwtTokenContainer = jwtUtil.buildJwtToken(registrationEntity);

        return new LoginResponse().message("Login successful").token(jwtTokenContainer.getJwtToken());
    }

    @Override
    @Secured(SecurityRule.IS_ANONYMOUS)
    public RegisterResponse register(RegisterRequest registerRequest) {
        RegistrationEntity tempRegistrationEntity = registrationRepository.findByCredentialsEntityEmail(registerRequest.getCredentials().getEmail()).orElse(null);
        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUserEntity(mapper.map(registerRequest.getUserData()));

        registrationEntity.setCredentialsEntity(mapper.map(registerRequest.getCredentials()));

        registrationEntity.getCredentialsEntity().setPassword(hasher.hash(registerRequest.getCredentials().getPassword()));


        if (registrationRepository.count() == 0) {
            registrationEntity.getUserEntity().setRole(ROLE_ADMIN);
        } else {
            registrationEntity.getUserEntity().setRole(ROLE_CUSTOMER);
        }

        registrationRepository.save(registrationEntity);

        JwtTokenContainer jwtTokenContainer = jwtUtil.buildJwtToken(registrationEntity);

        String sessionIdString = ((UUID) jwtTokenContainer.getClaims().get("sid")).toString();

        UserRegisteredEvent userRegisteredEvent = buildUserRegisteredEvent(registerRequest, registrationEntity, UUID.fromString(sessionIdString), registrationEntity.getCredentialsEntity().getEmail());
        userRegisteredProducer.send(userRegisteredEvent);
        return new RegisterResponse()
                .message("User registered successfully")
                .token(jwtTokenContainer.getJwtToken());
    }


    @Override
    @RolesAllowed({"admin", "customer"})
    public UpdateCredentialsResponse updateCredentials(UserUpdateCredentialsRequest userUpdateCredentialsRequest) {
        RegistrationEntity tempRegistrationEntity = registrationRepository.findByCredentialsEntityEmail(userUpdateCredentialsRequest.getEmail()).orElse(null);
        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        UUID userId = UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub"));
        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(userId).orElse(null);

        if(registrationEntity == null){
            throw new EhMaException(404, "User not found.");
        }

        if(!hasher.check(userUpdateCredentialsRequest.getNewPassword(), registrationEntity.getCredentialsEntity().getPassword())){
            throw new EhMaException(400, "Old password is wrong.");
        }

        registrationEntity.setCredentialsEntity(mapper.map(userUpdateCredentialsRequest));

        registrationEntity.getCredentialsEntity().setPassword(hasher.hash(userUpdateCredentialsRequest.getNewPassword()));

        registrationRepository.update(registrationEntity);
        JwtTokenContainer jwtTokenContainer = jwtUtil.buildJwtTokenKeepSession(registrationEntity, UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")));

        userCredentialsUpdatedProducer.send(buildUserCredentialsUpdatedEvent(userId, registrationEntity, UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")), registrationEntity.getCredentialsEntity().getEmail()));

        return new UpdateCredentialsResponse().message("Credentials updated successfully").token(jwtTokenContainer.getJwtToken());

    }

    @Override
    @RolesAllowed({"admin"})
    public SuccessResponse adminUpdateCredentials(UUID id, AdminUpdateCredentialsRequest adminUpdateCredentialsRequest) {
        RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(id).orElse(null);
        if(registrationEntity == null){
            throw new EhMaException(404, "User not found.");
        }

        RegistrationEntity tempRegistrationEntity = registrationRepository.findByCredentialsEntityEmail(adminUpdateCredentialsRequest.getEmail()).orElse(null);

        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }


        registrationEntity.setCredentialsEntity(mapper.map(adminUpdateCredentialsRequest));

        registrationEntity.getCredentialsEntity().setPassword(hasher.hash(adminUpdateCredentialsRequest.getPassword()));

        registrationRepository.update(registrationEntity);

        userCredentialsUpdatedProducer.send(buildUserCredentialsUpdatedEvent(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")), registrationEntity, UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")), jwtUtil.getClaimFromSecurityContext("email")));

        return new SuccessResponse().message("Credentials updated successfully");
    }


    private UserRegisteredEvent buildUserRegisteredEvent(RegisterRequest registerRequest, RegistrationEntity registrationEntity, UUID sessionId, String userEmail) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent().setUserPayload(mapper.map(registerRequest));
        userRegisteredEvent.getUserPayload().setRole(registrationEntity.getUserEntity().getRole());
        userRegisteredEvent.getUserPayload().setId(registrationEntity.getUserEntity().getId());
        userRegisteredEvent.setTimestamp(LocalDateTime.now());
        userRegisteredEvent.setUserId(registrationEntity.getUserEntity().getId());
        userRegisteredEvent.setUserEmail(userEmail);
        userRegisteredEvent.setSessionId(sessionId);
        userRegisteredEvent.setRequestCorrelationId(UUID.randomUUID());
        return userRegisteredEvent;
    }

    private UserCredentialsUpdatedEvent buildUserCredentialsUpdatedEvent(UUID userId, RegistrationEntity registrationEntity, UUID sessionId, String userEmail) {
        UserCredentialsUpdatedEvent userCredentialsUpdatedEvent = new UserCredentialsUpdatedEvent();
        userCredentialsUpdatedEvent.setUserEmail(userEmail);
        userCredentialsUpdatedEvent.setUserId(userId);
        userCredentialsUpdatedEvent.setTimestamp(LocalDateTime.now());
        userCredentialsUpdatedEvent.setId(userId);
        userCredentialsUpdatedEvent.setSessionId(sessionId);
        userCredentialsUpdatedEvent.setEmail(registrationEntity.getCredentialsEntity().getEmail());
        userCredentialsUpdatedEvent.setRequestCorrelationId(UUID.randomUUID());
        return userCredentialsUpdatedEvent;
    }

}
