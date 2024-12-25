package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.AuthApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.auth.api.AuthenticationAuthRestClient;
import com.hrbat.quarkus.master.apigateway.model.auth.api.CredentialsAuthRestClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class AuthApiService implements AuthApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    AuthenticationAuthRestClient authRestClient;

    @Inject
    @RestClient
    CredentialsAuthRestClient credentialsAuthRestClient;

    @Override
    @RolesAllowed({"admin"})
    public SuccessResponse adminUpdateCredentials(UUID id, AdminUpdateCredentialsRequest adminUpdateCredentialsRequest) {
        return mapper.map(credentialsAuthRestClient.adminUpdateCredentials(id, mapper.map(adminUpdateCredentialsRequest)));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return mapper.map(authRestClient.login(mapper.map(loginRequest)));
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        return mapper.map(authRestClient.register(mapper.map(registerRequest)));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public UpdateCredentialsResponse updateCredentials(UserUpdateCredentialsRequest userUpdateCredentialsRequest) {
        return mapper.map(credentialsAuthRestClient.updateCredentials(mapper.map(userUpdateCredentialsRequest)));
    }
}
