package com.hrbatovic.springboot.master.order.api;

import com.hrbatovic.master.springboot.order.api.OrdersApi;
import com.hrbatovic.master.springboot.order.api.UsersApi;
import com.hrbatovic.master.springboot.order.model.Order;
import com.hrbatovic.master.springboot.order.model.OrderListResponse;
import com.hrbatovic.master.springboot.order.model.OrderListResponsePagination;
import com.hrbatovic.springboot.master.order.ClaimUtils;
import com.hrbatovic.springboot.master.order.db.entities.OrderEntity;
import com.hrbatovic.springboot.master.order.db.repositories.OrderRepository;
import com.hrbatovic.springboot.master.order.exceptions.EhMaException;
import com.hrbatovic.springboot.master.order.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderApiService implements OrdersApi, UsersApi {

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);

        if(orderEntity == null){
            throw new EhMaException(404, "Order not found");
        }


        return mapper.map(orderEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {

        if (claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")) {
            if (!userId.equals(claimUtils.getUUIDClaim("sub"))) {
                throw new EhMaException(400, "You are not allowed to view other user's orders.");
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
