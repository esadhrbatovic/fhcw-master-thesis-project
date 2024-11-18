package com.hrbatovic.master.user.api;

import com.hrbatovic.master.quarkus.user.api.UsersApi;
import com.hrbatovic.master.quarkus.user.model.DeleteUserResponse;
import com.hrbatovic.master.quarkus.user.model.UpdateUserProfileRequest;
import com.hrbatovic.master.quarkus.user.model.UserProfileResponse;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class UserApiService implements UsersApi {

    @Override
    public UserProfileResponse getUser(UUID id) {
        return null;
    }

    @Override
    public UserProfileResponse updateUser(UUID id, UpdateUserProfileRequest updateUserProfileRequest) {
        return null;
    }

    @Override
    public DeleteUserResponse deleteUser(UUID id) {
        return null;
    }

}

