package com.hrbatovic.quarkus.master.auth.api.resources;

import com.hrbatovic.master.quarkus.auth.api.AuthApi;
import com.hrbatovic.master.quarkus.auth.model.*;
import com.hrbatovic.quarkus.master.auth.ApiInputValidator;
import com.hrbatovic.quarkus.master.auth.JwtUtil;
import com.hrbatovic.quarkus.master.auth.Hasher;
import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.quarkus.master.auth.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.auth.mapper.MapUtil;
import com.hrbatovic.quarkus.master.auth.messaging.model.out.UserCredentialsUpdatedEvent;
import com.hrbatovic.quarkus.master.auth.messaging.model.out.UserRegisteredEvent;
import io.vertx.codegen.type.ApiTypeInfo;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class AuthApiService implements AuthApi {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_CUSTOMER = "customer";

    @Inject
    JwtUtil jwtUtil;

    @Inject
    Hasher passwordHasher;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    @Claim(standard = Claims.email)
    String emailClaim;

    @Inject
    @Claim("sid")
    String sessionIdClaim;

    @Inject
    @Channel("user-registered-out")
    Emitter<UserRegisteredEvent> userRegisteredEmitter;

    @Inject
    @Channel("user-credentials-updated-out")
    Emitter<UserCredentialsUpdatedEvent> userCredentialsUpdatedEmitter;

    @Inject
    MapUtil mapper;

    @Override
    public Response login(LoginRequest loginRequest) {
        ApiInputValidator.validateLogin(loginRequest);

        RegistrationEntity registrationEntity = RegistrationEntity.findByEmail(loginRequest.getCredentials().getEmail());

        if (registrationEntity == null) {
            throw new EhMaException(400, "Login Failed");
        }

        if (!passwordHasher.check(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())) {

            throw new EhMaException(400, "Login Failed");
        }

        return Response.ok(new LoginResponse().message("Logged in successfully").token(jwtUtil.buildJwtToken(registrationEntity))).status(200).build();
    }

    @Override
    public Response register(RegisterRequest registerRequest) {
        ApiInputValidator.validateRegistration(registerRequest);
        RegistrationEntity tempRegistrationEntity = RegistrationEntity.findByEmail(registerRequest.getCredentials().getEmail());
        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUserEntity(mapper.map(registerRequest.getUserData()));

        registrationEntity.setCredentialsEntity(mapper.map(registerRequest.getCredentials()));

        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(registerRequest.getCredentials().getPassword()));

        if (RegistrationEntity.count() == 0) {
            registrationEntity.getUserEntity().setRole(ROLE_ADMIN);
        } else {
            registrationEntity.getUserEntity().setRole(ROLE_CUSTOMER);
        }

        registrationEntity.persist();

        String jwtToken = jwtUtil.buildJwtToken(registrationEntity);

        String sessionIdString = jwtUtil.extractClaim(jwtToken, "sid");

        userRegisteredEmitter.send(buildUserRegisteredEvent(registerRequest, registrationEntity, UUID.fromString(sessionIdString), registrationEntity.getCredentialsEntity().getEmail()));

        return Response.ok(new RegisterResponse().message("User registered successfully").token(jwtToken)).status(200).build();
    }


    @Override
    @RolesAllowed({"admin", "customer"})
    public Response updateCredentials(UserUpdateCredentialsRequest updateCredentialsRequest) {
        ApiInputValidator.validateUpdateCredentials(updateCredentialsRequest);
        RegistrationEntity tempRegistrationEntity = RegistrationEntity.findByEmail(updateCredentialsRequest.getEmail());
        if(tempRegistrationEntity == null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        UUID userId = UUID.fromString(userSubClaim);
        RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(userId);

        if (registrationEntity == null) {
            throw new EhMaException(400, "User not found");
        }
        if (!passwordHasher.check(updateCredentialsRequest.getOldPassword(), registrationEntity.getCredentialsEntity().getPassword())) {
            throw new EhMaException(400, "Old password is wrong");
        }

        registrationEntity.setCredentialsEntity(mapper.map(updateCredentialsRequest));

        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(updateCredentialsRequest.getNewPassword()));

        registrationEntity.update();
        String jwtToken = jwtUtil.buildJwtTokenKeepSession(registrationEntity, UUID.fromString(sessionIdClaim));

        userCredentialsUpdatedEmitter.send(buildUserCredentialsUpdatedEvent(userId, registrationEntity, UUID.fromString(sessionIdClaim), registrationEntity.getCredentialsEntity().getEmail()));

        return Response.ok(new UpdateCredentialsResponse().message("Credentials updated successfully").token(jwtToken)).status(200).build();
    }

    @Override
    @RolesAllowed({"admin"})
    public Response adminUpdateCredentials(UUID userId, AdminUpdateCredentialsRequest updateCredentialsRequest) {
        ApiInputValidator.validateAdminUpdateCredentials(updateCredentialsRequest);
        RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(userId);
        if (registrationEntity == null) {
            throw new EhMaException(400, "User not found");
        }

        RegistrationEntity tempRegistrationEntity = RegistrationEntity.findByEmail(updateCredentialsRequest.getEmail());
        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        registrationEntity.setCredentialsEntity(mapper.map(updateCredentialsRequest));

        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(updateCredentialsRequest.getPassword()));

        registrationEntity.update();

        userCredentialsUpdatedEmitter.send(buildUserCredentialsUpdatedEvent(UUID.fromString(userSubClaim), registrationEntity, UUID.fromString(sessionIdClaim), emailClaim));

        return Response.ok(new SuccessResponse().message("Credentials updated successfully")).status(200).build();
    }

    private UserRegisteredEvent buildUserRegisteredEvent(RegisterRequest registerRequest, RegistrationEntity registrationEntity, UUID sessionId, String userEmail) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent().setUserPayload(mapper.map(registerRequest));
        userRegisteredEvent.getUserPayload().setRole(registrationEntity.getUserEntity().getRole());
        userRegisteredEvent.getUserPayload().setId(registrationEntity.getUserEntity().id);
        userRegisteredEvent.setTimestamp(LocalDateTime.now());
        userRegisteredEvent.setUserId(registrationEntity.getUserEntity().id);
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
