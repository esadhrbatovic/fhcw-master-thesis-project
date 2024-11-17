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
    public DeleteUserResponse deleteUser(UUID userId) {
        return null;
    }

    @Override
    public UserProfileResponse getUserProfile(UUID userId) {
        return null;
    }

    @Override
    public UserProfileResponse updateUserProfile(UUID userId, UpdateUserProfileRequest updateUserProfileRequest) {
        return null;
    }
}
