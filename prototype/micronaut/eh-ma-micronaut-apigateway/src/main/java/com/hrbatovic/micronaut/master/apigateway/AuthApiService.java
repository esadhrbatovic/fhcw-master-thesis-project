package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.AuthenticationApi;
import com.hrbatovic.micronaut.master.apigateway.api.CredentialsApi;
import com.hrbatovic.micronaut.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.exceptions.HttpException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
@ReflectiveAccess
public class AuthApiService implements AuthenticationApi, CredentialsApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.auth.api.AuthenticationApi authenticationApiClient;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.auth.api.CredentialsApi credentialsApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            return mapper.map(authenticationApiClient.login(mapper.map(loginRequest)));
        } catch (HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            return mapper.map(authenticationApiClient.register(mapper.map(registerRequest)));
        } catch (HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"admin"})
    @ExecuteOn(TaskExecutors.BLOCKING)
    public SuccessResponse adminUpdateCredentials(UUID id, String authorization, AdminUpdateCredentialsRequest adminUpdateCredentialsRequest) {
        try {
            return mapper.map(credentialsApiClient.adminUpdateCredentials(id, authorization, mapper.map(adminUpdateCredentialsRequest)));
        } catch (HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public UpdateCredentialsResponse updateCredentials(String authorization, UserUpdateCredentialsRequest userUpdateCredentialsRequest) {
        try {
            return mapper.map(credentialsApiClient.updateCredentials(authorization, mapper.map(userUpdateCredentialsRequest)));
        } catch (HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }
}
