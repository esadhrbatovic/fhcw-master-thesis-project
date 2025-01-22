package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.apigateway.clients.auth.api.AuthenticationApi;
import com.hrbatovic.master.springboot.apigateway.clients.auth.api.CredentialsApi;
import com.hrbatovic.master.springboot.gateway.api.AuthApi;
import com.hrbatovic.master.springboot.gateway.model.*;
import com.hrbatovic.springboot.master.apigateway.JwtAuthentication;
import com.hrbatovic.springboot.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.springboot.master.apigateway.exceptions.EhMaExceptionHandler;
import com.hrbatovic.springboot.master.apigateway.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.hrbatovic.springboot.master.apigateway.JsonErrorParser.parseErrorMessage;

@RestController
public class AuthApiService implements AuthApi {

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse adminUpdateCredentials(UUID id, AdminUpdateCredentialsRequest adminUpdateCredentialsRequest) {
        try {
            CredentialsApi credentialsApi = new CredentialsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                credentialsApi.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                SuccessResponse response = mapper.map(credentialsApi.adminUpdateCredentials(id, mapper.map(adminUpdateCredentialsRequest)));
                return response;
            }

            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            AuthenticationApi authenticationApi = new AuthenticationApi();
            LoginResponse response = mapper.map(authenticationApi.login(mapper.map(loginRequest)));
            return response;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        try {
            AuthenticationApi authenticationApi = new AuthenticationApi();
            RegisterResponse response = mapper.map(authenticationApi.register(mapper.map(registerRequest)));
            return response;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public UpdateCredentialsResponse updateCredentials(UserUpdateCredentialsRequest userUpdateCredentialsRequest) {
        try {
            CredentialsApi credentialsApi = new CredentialsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                credentialsApi.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                UpdateCredentialsResponse response = mapper.map(credentialsApi.updateCredentials(mapper.map(userUpdateCredentialsRequest)));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
