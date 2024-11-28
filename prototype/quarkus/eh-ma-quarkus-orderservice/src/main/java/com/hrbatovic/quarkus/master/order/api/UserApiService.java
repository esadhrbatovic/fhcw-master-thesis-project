package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.UsersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class UserApiService implements UsersApi {

    @Override
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {
        return null;
    }
}
