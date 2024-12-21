package com.hrbatovic.quarkus.master.auth;

import com.hrbatovic.master.quarkus.auth.model.AdminUpdateCredentialsRequest;
import com.hrbatovic.master.quarkus.auth.model.LoginRequest;
import com.hrbatovic.master.quarkus.auth.model.RegisterRequest;
import com.hrbatovic.master.quarkus.auth.model.UserUpdateCredentialsRequest;
import com.hrbatovic.quarkus.master.auth.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;


public abstract class ApiInputValidator {


    public static void validateLogin(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getCredentials() == null
        || StringUtils.isEmpty(loginRequest.getCredentials().getEmail()) || StringUtils.isEmpty(loginRequest.getCredentials().getPassword())){
            throw new EhMaException(400, "You did not provide all login information.");
        }
    }

    public static void validateRegistration(RegisterRequest registerRequest) {
        if(registerRequest == null || registerRequest.getUserData() == null ||
        StringUtils.isEmpty(registerRequest.getUserData().getFirstName())
                || StringUtils.isEmpty(registerRequest.getUserData().getLastName())
                || StringUtils.isEmpty(registerRequest.getUserData().getPhoneNumber())
                || registerRequest.getUserData().getAddress() == null
                || StringUtils.isEmpty(registerRequest.getUserData().getAddress().getCity())
                || StringUtils.isEmpty(registerRequest.getUserData().getAddress().getCountry())
                || StringUtils.isEmpty(registerRequest.getUserData().getAddress().getState())
                || StringUtils.isEmpty(registerRequest.getUserData().getAddress().getStreet())
                || StringUtils.isEmpty(registerRequest.getUserData().getAddress().getPostalCode())
        ){
            throw new EhMaException(400, "You did not provide all register information.");
        }
    }

    public static void validateUpdateCredentials(UserUpdateCredentialsRequest updateCredentialsRequest) {
        if(updateCredentialsRequest == null || StringUtils.isEmpty(updateCredentialsRequest.getEmail()) ||
        StringUtils.isEmpty(updateCredentialsRequest.getNewPassword()) ||
        StringUtils.isEmpty(updateCredentialsRequest.getOldPassword())){
            throw new EhMaException(400, "You did not provide all credentials information");
        }
    }

    public static void validateAdminUpdateCredentials(AdminUpdateCredentialsRequest updateCredentialsRequest) {
        if(updateCredentialsRequest == null || StringUtils.isEmpty(updateCredentialsRequest.getEmail()) ||
        StringUtils.isEmpty(updateCredentialsRequest.getPassword())){
            throw new EhMaException(400, "You did not provide all credentials information.");
        }
    }
}
