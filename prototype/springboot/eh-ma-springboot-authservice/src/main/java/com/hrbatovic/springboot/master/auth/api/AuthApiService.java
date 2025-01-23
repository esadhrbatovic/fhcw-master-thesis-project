package com.hrbatovic.springboot.master.auth.api;

import com.hrbatovic.master.springboot.auth.api.AuthApi;
import com.hrbatovic.master.springboot.auth.model.*;
import com.hrbatovic.springboot.master.auth.*;
import com.hrbatovic.springboot.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.springboot.master.auth.db.repositories.RegistrationRepository;
import com.hrbatovic.springboot.master.auth.exceptions.EhMaException;
import com.hrbatovic.springboot.master.auth.mapper.MapUtil;
import com.hrbatovic.springboot.master.auth.messaging.producers.UserCredentialsUpdatedProducer;
import com.hrbatovic.springboot.master.auth.messaging.model.out.UserCredentialsUpdatedEvent;
import com.hrbatovic.springboot.master.auth.messaging.model.out.UserRegisteredEvent;
import com.hrbatovic.springboot.master.auth.messaging.producers.UserRegisteredEventProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class AuthApiService implements AuthApi {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MapUtil mapper;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    Hasher hasher;

    @Autowired
    UserRegisteredEventProducer userRegisteredEventProducer;

    @Autowired
    UserCredentialsUpdatedProducer userCredentialsUpdatedProducer;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        ApiInputValidator.validateLogin(loginRequest);
        RegistrationEntity registrationEntity = registrationRepository.findByCredentialsEntityEmail(loginRequest.getCredentials().getEmail()).orElse(null);

        if (registrationEntity == null) {
            throw new EhMaException(400, "Login Failed.");
        }

        if(!hasher.check(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())){
            throw new EhMaException(400, "Login Failed.");
        }

        JwtTokenContainer jwtTokenContainer = jwtUtil.buildJwtToken(registrationEntity);

        return new LoginResponse().message("Login successful").token(jwtTokenContainer.getJwtToken());
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        ApiInputValidator.validateRegistration(registerRequest);
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

        String sessionIdString = (String) jwtTokenContainer.getClaims().get("sid");

        UserRegisteredEvent userRegisteredEvent = buildUserRegisteredEvent(registerRequest, registrationEntity, UUID.fromString(sessionIdString), registrationEntity.getCredentialsEntity().getEmail());
        userRegisteredEventProducer.send(userRegisteredEvent);
        return new RegisterResponse()
                .message("User registered successfully")
                .token(jwtTokenContainer.getJwtToken());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public UpdateCredentialsResponse updateCredentials(UserUpdateCredentialsRequest userUpdateCredentialsRequest) {
        ApiInputValidator.validateUpdateCredentials(userUpdateCredentialsRequest);

        RegistrationEntity tempRegistrationEntity = registrationRepository.findByCredentialsEntityEmail(userUpdateCredentialsRequest.getEmail()).orElse(null);
        if(tempRegistrationEntity != null){
            throw new EhMaException(400, "This e-mail is not available.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthentication jwtAuth) {

            UUID userId = jwtAuth.getUserId();
            RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(userId).orElse(null);
            if(registrationEntity == null){
                throw new RuntimeException("User not found.");
            }

            if(!hasher.check(userUpdateCredentialsRequest.getNewPassword(), registrationEntity.getCredentialsEntity().getPassword())){
                throw new RuntimeException("Old password is wrong.");
            }

            //TODO: adjust this on other services
            if(StringUtils.isEmpty(userUpdateCredentialsRequest.getEmail())){
                userUpdateCredentialsRequest.setEmail(registrationEntity.getCredentialsEntity().getEmail());
            }

            registrationEntity.setCredentialsEntity(mapper.map(userUpdateCredentialsRequest));
            registrationEntity.getCredentialsEntity().setPassword(hasher.hash(userUpdateCredentialsRequest.getNewPassword()));
            registrationRepository.save(registrationEntity);
            JwtTokenContainer jwtTokenContainer = jwtUtil.buildJwtTokenKeepSession(registrationEntity, jwtAuth.getSessionId());
            userCredentialsUpdatedProducer.send(buildUserCredentialsUpdatedEvent(userId, registrationEntity, jwtAuth.getSessionId(), registrationEntity.getCredentialsEntity().getEmail()));

            return new UpdateCredentialsResponse().message("Credentials updated successfully").token(jwtTokenContainer.getJwtToken());
        }

        return  null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse adminUpdateCredentials(UUID id, AdminUpdateCredentialsRequest adminUpdateCredentialsRequest) {
        ApiInputValidator.validateAdminUpdateCredentials(adminUpdateCredentialsRequest);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthentication jwtAuth) {
            RegistrationEntity registrationEntity = registrationRepository.findByUserEntityId(id).orElse(null);
            if (registrationEntity == null) {
                throw new EhMaException(400, "User not found");
            }

            RegistrationEntity tempRegistrationEntity = registrationRepository.findByCredentialsEntityEmail(adminUpdateCredentialsRequest.getEmail()).orElse(null);
            if (tempRegistrationEntity != null) {
                throw new EhMaException(400, "This e-mail is not available.");
            }

            registrationEntity.setCredentialsEntity(mapper.map(adminUpdateCredentialsRequest));

            registrationEntity.getCredentialsEntity().setPassword(hasher.hash(adminUpdateCredentialsRequest.getPassword()));

            registrationRepository.save(registrationEntity);

            userCredentialsUpdatedProducer.send(buildUserCredentialsUpdatedEvent(jwtAuth.getUserId(), registrationEntity, jwtAuth.getSessionId(), jwtAuth.getEmail()));

            return null;
        }
        return null;
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
