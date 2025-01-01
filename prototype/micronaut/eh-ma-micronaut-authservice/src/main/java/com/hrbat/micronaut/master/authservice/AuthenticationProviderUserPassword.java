package com.hrbat.micronaut.master.authservice;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Override
    public @NonNull AuthenticationResponse authenticate(Object requestContext, @NonNull AuthenticationRequest authRequest) {

        return AuthenticationResponse.success("test", List.of("roles"));
    }
}