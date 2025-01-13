package com.hrbatovic.micronaut.master.order.api;

import com.hrbatovic.micronaut.master.order.JwtUtil;
import com.hrbatovic.micronaut.master.order.db.entities.OrderEntity;
import com.hrbatovic.micronaut.master.order.db.repositories.OrderRepository;
import com.hrbatovic.micronaut.master.order.mapper.MapUtil;
import com.hrbatovic.micronaut.master.order.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class OrderApiService implements OrdersApi{

    @Inject
    JwtUtil jwtUtil;

    @Inject
    OrderRepository orderRepository;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin"})
    public OrderListResponse getAllOrders(Integer page, Integer limit, GetAllOrdersStatusParameter status, GetAllOrdersSortParameter sort) {

        String sortString = sort != null ? sort.toString() : null;
        String statusString = status != null ? status.toString() : null;

        List<OrderEntity> orders = orderRepository.queryOrders(page, limit, statusString, sortString);

        OrderListResponse response = new OrderListResponse();
        response.setOrders(mapper.toOrderList(orders));

        OrderListResponsePagination pagination = new OrderListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems(orders.size());
        pagination.setTotalPages((int) Math.ceil((double) orders.size() / (limit != null ? limit : 10)));

        response.setPagination(pagination);

        return response;
    }

    @Override
    @RolesAllowed({"admin"})
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);

        if(orderEntity == null){
            throw new RuntimeException("Order not found");
        }


        return mapper.map(orderEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, GetAllOrdersStatusParameter status, GetAllOrdersSortParameter sort) {

        if (jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")) {
            if (!userId.equals(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))) {
                throw new RuntimeException("You are not allowed to view other user's orders.");
            }
        }

        String statusString = status != null ? status.toString() : null;
        String sortString = sort != null ? sort.toString() : null;

        List<OrderEntity> orders = orderRepository.queryUserOrders(userId, page, limit, statusString, sortString);

        OrderListResponse response = new OrderListResponse();
        response.setOrders(mapper.toOrderList(orders));

        OrderListResponsePagination pagination = new OrderListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems(orders.size());
        pagination.setTotalPages((int) Math.ceil((double) orders.size() / (limit != null ? limit : 10)));

        response.setPagination(pagination);
        return response;
    }
}
