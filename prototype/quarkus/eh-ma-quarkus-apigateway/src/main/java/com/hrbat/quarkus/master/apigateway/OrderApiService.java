package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.OrdersApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.Order;
import com.hrbat.quarkus.master.apigateway.model.OrderListResponse;
import com.hrbat.quarkus.master.apigateway.model.order.api.OrdersOrderRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class OrderApiService implements OrdersApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    OrdersOrderRestClient ordersOrderRestClient;

    @Override
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {
        return mapper.map(ordersOrderRestClient.getAllOrders(page, limit, status, sort));
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return mapper.map(ordersOrderRestClient.getOrderById(orderId));
    }
}
