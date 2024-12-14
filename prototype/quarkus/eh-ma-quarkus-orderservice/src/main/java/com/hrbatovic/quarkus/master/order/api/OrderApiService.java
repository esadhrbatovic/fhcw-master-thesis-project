package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.OrdersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderListResponsePagination;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class OrderApiService implements OrdersApi {

    @Inject
    MapUtil mapper;

    @Override
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {
        PanacheQuery<OrderEntity> query = OrderEntity.buildQuery(status, sort);

        Page pagination = Page.of(
                (page != null && page > 0) ? page - 1 : 0,
                (limit != null && limit > 0) ? limit : 10
        );

        query.page(pagination);

        return createOrderListResponse(query.list(), query.pageCount(), query.count(), page, limit);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = OrderEntity.findById(orderId);

        if(orderEntity == null){
            throw new RuntimeException("Order not found");
        }

        return mapper.map(orderEntity);
    }

    private OrderListResponse createOrderListResponse(
            List<OrderEntity> orderEntities,
            int totalPages,
            long totalItems,
            Integer page,
            Integer limit
    ) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(mapper.toOrderList(orderEntities));

        OrderListResponsePagination pagination = new OrderListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        response.setPagination(pagination);
        return response;
    }
}
