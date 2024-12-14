package com.hrbatovic.quarkus.master.auth.api.resources;

import com.hrbatovic.master.quarkus.auth.api.AuthApi;
import com.hrbatovic.master.quarkus.auth.model.*;
import com.hrbatovic.quarkus.master.auth.JwtBuilder;
import com.hrbatovic.quarkus.master.auth.Hasher;
import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.quarkus.master.auth.mapper.MapUtil;
import com.hrbatovic.quarkus.master.auth.messaging.model.out.UserRegisteredEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.UUID;

@RequestScoped
public class AuthApiService implements AuthApi {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_CUSTOMER = "customer";

    @Inject
    JwtBuilder jwtBuilder;

    @Inject
    Hasher passwordHasher;

    @Inject
    @Claim(standard = Claims.sub)
    String userSub;

    @Inject
    @Channel("user-registered-out")
    Emitter<UserRegisteredEvent> userRegisteredEmitter;

    @Inject
    MapUtil mapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        //TODO: api validation, error handling, authorisation

        RegistrationEntity registrationEntity = RegistrationEntity.findByEmail(loginRequest.getCredentials().getEmail());

        if(registrationEntity == null){
            throw new RuntimeException("Login Failed");
        }

        if(!passwordHasher.check(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())){

            throw new RuntimeException("Login Failed");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Logged in successfully");
        loginResponse.setToken(jwtBuilder.buildJwtToken(registrationEntity));

        return loginResponse;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        //TODO: api validation, error handling, authorisation

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

        UserRegisteredEvent userRegisteredEvent = mapper.map(registerRequest);
        userRegisteredEvent.setRole(registrationEntity.getUserEntity().getRole());
        userRegisteredEvent.setId(registrationEntity.getUserEntity().id);
        userRegisteredEmitter.send(userRegisteredEvent);

        return new RegisterResponse().message("User registered successfully").token(jwtBuilder.buildJwtToken(registrationEntity));
    }

    @Override
    public UpdateCredentialsResponse updateCredentials(UserUpdateCredentialsRequest updateCredentialsRequest) {
        UUID userId = UUID.fromString(userSub);
        RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(userId);
        if(registrationEntity == null){
            throw new RuntimeException("User not found");
        }
        if(!passwordHasher.check(updateCredentialsRequest.getOldPassword(), registrationEntity.getCredentialsEntity().getPassword())){
            throw new RuntimeException("Old password is wrong");
        }

        registrationEntity.setCredentialsEntity(mapper.map(updateCredentialsRequest));


        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(updateCredentialsRequest.getNewPassword()));

        registrationEntity.update();

        //TODO: emit credentials updated event - other services need updated email

        return new UpdateCredentialsResponse().message("Credentials updated successfully").token(jwtBuilder.buildJwtToken(registrationEntity));
    }


    @Override
    public SuccessResponse adminUpdateCredentials(UUID id, AdminUpdateCredentialsRequest updateCredentialsRequest) {
        RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(id);
        if(registrationEntity == null){
            throw new RuntimeException("User not found");
        }

        registrationEntity.setCredentialsEntity(mapper.map(updateCredentialsRequest));

        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(updateCredentialsRequest.getPassword()));

        registrationEntity.update();

        //TODO: emit credentials updated event - other services need updated email

        return new SuccessResponse().message("Credentials updated successfully");
    }

}
