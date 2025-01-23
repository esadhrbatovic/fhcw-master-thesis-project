package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.UserApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Singleton
public class UserApiService implements UserApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.user.api.UserApi userApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public DeleteUserResponse deleteUser(UUID id, String authorization) {
        return mapper.map(userApiClient.deleteUser(id, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse getUser(UUID id, String authorization) {
        return mapper.map(userApiClient.getUser(id, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public UserListResponse listUsers(String authorization, Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, ListUsersSortParameter sort) {
        return mapper.map(userApiClient.listUsers(authorization, page, limit, search, createdAfter, createdBefore, mapper.map(sort)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse updateUser(UUID id, String authorization, UpdateUserProfileRequest updateUserProfileRequest) {
        return mapper.map(userApiClient.updateUser(id, authorization, mapper.map(updateUserProfileRequest)));
    }

}
