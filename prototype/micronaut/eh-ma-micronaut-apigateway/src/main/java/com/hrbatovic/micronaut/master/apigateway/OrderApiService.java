package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.OrdersApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.GetAllOrdersSortParameter;
import com.hrbatovic.micronaut.master.apigateway.model.GetAllOrdersStatusParameter;
import com.hrbatovic.micronaut.master.apigateway.model.Order;
import com.hrbatovic.micronaut.master.apigateway.model.OrderListResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
public class OrderApiService implements OrdersApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.order.api.OrdersApi ordersApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public OrderListResponse getAllOrders(String authorization, Integer page, Integer limit, GetAllOrdersStatusParameter status, GetAllOrdersSortParameter sort) {
        return mapper.map(ordersApiClient.getAllOrders(authorization, page, limit, mapper.map(status), mapper.map(sort)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public Order getOrderById(UUID orderId, String authorization) {
        return mapper.map(ordersApiClient.getOrderById(orderId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public OrderListResponse getUserOrders(UUID userId, String authorization, Integer page, Integer limit, GetAllOrdersStatusParameter status, GetAllOrdersSortParameter sort) {
        return mapper.map(ordersApiClient.getUserOrders(userId, authorization, page, limit, mapper.map(status), mapper.map(sort)));
    }
}
