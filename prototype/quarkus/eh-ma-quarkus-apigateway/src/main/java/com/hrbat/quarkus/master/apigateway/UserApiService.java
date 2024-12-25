package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.UsersApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.order.api.OrdersOrderRestClient;
import com.hrbat.quarkus.master.apigateway.model.user.api.UserUserRestClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class UserApiService implements UsersApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    UserUserRestClient userUserRestClient;

    @Inject
    @RestClient
    OrdersOrderRestClient ordersOrderRestClient;

    @Override
    @RolesAllowed({"admin", "customer"})
    public DeleteUserResponse deleteUser(UUID id) {
        return mapper.map(userUserRestClient.deleteUser(id));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse getUser(UUID id) {
        return mapper.map(userUserRestClient.getUser(id));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {
        return mapper.map(ordersOrderRestClient.getUserOrders(userId, page, limit, status, sort));
    }

    @Override
    @RolesAllowed({"admin"})
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        return mapper.map(userUserRestClient.listUsers(page, limit, search, createdAfter, createdBefore, sort));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse updateUser(UUID id, UpdateUserProfileRequest updateUserProfileRequest) {
        return mapper.map(userUserRestClient.updateUser(id, mapper.map(updateUserProfileRequest)));
    }
}
