package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.OrdersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.OrderResponse;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class OrderApiService implements OrdersApi {

    @Override
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        return null;
    }
}
