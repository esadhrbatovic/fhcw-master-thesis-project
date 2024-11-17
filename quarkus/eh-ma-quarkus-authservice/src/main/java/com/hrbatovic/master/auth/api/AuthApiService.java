package com.hrbatovic.master.auth.api;

import com.hrbatovic.master.quarkus.auth.api.AuthApi;
import com.hrbatovic.master.quarkus.auth.model.LoginRequest;
import com.hrbatovic.master.quarkus.auth.model.LoginResponse;
import com.hrbatovic.master.quarkus.auth.model.RegisterRequest;
import com.hrbatovic.master.quarkus.auth.model.RegisterResponse;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AuthApiService implements AuthApi {

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        return null;
    }
}
