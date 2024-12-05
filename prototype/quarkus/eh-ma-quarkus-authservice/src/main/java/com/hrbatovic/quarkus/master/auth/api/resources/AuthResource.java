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
public class AuthResource implements AuthApi {

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

    //TODO: also provide current credentials - for email or password update or both
    @Override
    public UpdateCredentialsResponse updateCredentials(UpdateCredentialsRequest updateCredentialsRequest) {
        //TODO: api validation, error handling, authorisation

        RegistrationEntity registrationEntity = RegistrationEntity.findByUserid(UUID.fromString(userSub));
        if(registrationEntity == null){
            throw new RuntimeException("User not found");
        }

        registrationEntity.setCredentialsEntity(mapper.map(updateCredentialsRequest.getCredentials()));

        registrationEntity.getCredentialsEntity().setPassword(passwordHasher.hash(updateCredentialsRequest.getCredentials().getPassword()));

        UpdateCredentialsResponse updateCredentialsResponse = new UpdateCredentialsResponse();

        updateCredentialsResponse.setMessage("Credentials updated successfully");
        updateCredentialsResponse.setToken(jwtBuilder.buildJwtToken(registrationEntity));

        return updateCredentialsResponse;
    }

    //TODO: admin change credentials endpoint
}
