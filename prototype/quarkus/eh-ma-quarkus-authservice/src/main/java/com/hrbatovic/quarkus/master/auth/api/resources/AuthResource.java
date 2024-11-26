package com.hrbatovic.quarkus.master.auth.api.resources;

import com.hrbatovic.master.quarkus.auth.api.AuthApi;
import com.hrbatovic.master.quarkus.auth.model.*;
import com.hrbatovic.quarkus.master.auth.JwtBuilder;
import com.hrbatovic.quarkus.master.auth.Hasher;
import com.hrbatovic.quarkus.master.auth.db.entities.AddressEntity;
import com.hrbatovic.quarkus.master.auth.db.entities.CredentialsEntity;
import com.hrbatovic.quarkus.master.auth.db.entities.RegistrationEntity;
import com.hrbatovic.quarkus.master.auth.db.entities.UserEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@RequestScoped
public class AuthResource implements AuthApi {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_CUSTOMER = "customer";

    @Inject
    JwtBuilder jwtBuilder;

    @Inject
    Hasher passwordHasher;

    @Inject
    @Channel("user-registered-out")
    Emitter<UserEntity> userRegisteredEmitter;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        RegistrationEntity registrationEntity = RegistrationEntity.findByEmail(loginRequest.getCredentials().getEmail());

        if(registrationEntity == null){
            throw new RuntimeException("Login Failed");
        }

        if(!passwordHasher.check(loginRequest.getCredentials().getPassword(), registrationEntity.getCredentialsEntity().getPassword())){

            throw new RuntimeException("Login Failed");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Logged in successfully");
        loginResponse.setToken(jwtBuilder.buildJwtToken(registrationEntity.getUserEntity()));

        return loginResponse;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUserEntity(map(registerRequest.getUserData()));
        registrationEntity.setCredentialsEntity(map(registerRequest.getCredentials()));
        registrationEntity.getUserEntity().setEmail(registerRequest.getCredentials().getEmail());

        if (RegistrationEntity.count() == 0) {
            registrationEntity.getUserEntity().setRole(ROLE_ADMIN);
        } else {
            registrationEntity.getUserEntity().setRole(ROLE_CUSTOMER);
        }

        registrationEntity.persist();

        RegisterResponse registerResponse = new RegisterResponse();

        userRegisteredEmitter.send(registrationEntity.getUserEntity());

        registerResponse.setMessage("User registered successfully");
        registerResponse.setToken(jwtBuilder.buildJwtToken(registrationEntity.getUserEntity()));
        return registerResponse;
    }

    private UserEntity map(UserForm userData) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userData.getFirstName());
        userEntity.setLastName(userData.getLastName());
        userEntity.setAddress(map(userData.getAddress()));
        userEntity.setPhoneNumber(userData.getPhoneNumber());
        return userEntity;
    }

    private AddressEntity map(AddressForm addressForm) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressForm.getCity());
        addressEntity.setCountry(addressForm.getCountry());
        addressEntity.setPostalCode(addressForm.getPostalCode());
        addressEntity.setState(addressForm.getState());
        addressEntity.setStreet(addressForm.getStreet());

        return addressEntity;
    }

    private CredentialsEntity map(CredentialsForm credentialsForm) {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setPassword(passwordHasher.hash(credentialsForm.getPassword()));
        credentialsEntity.setEmail(credentialsForm.getEmail());
        return credentialsEntity;
    }
}
