package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.UsersApi;
import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.OrderListResponsePagination;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.mapper.Mapper;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class UserApiService implements UsersApi {

    @Override
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {

        if (userId == null) {
            throw new WebApplicationException("User ID is required.", Response.Status.BAD_REQUEST);
        }

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        conditions.add("{ 'userId': :userId }");
        params.put("userId", userId);

        if (status != null && !status.isEmpty()) {
            conditions.add("{ 'status': :status }");
            params.put("status", status);
        }

        queryBuilder.append("{ '$and': [ ").append(String.join(", ", conditions)).append(" ] }");

        Sort sortOrder = null;
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "dateAsc":
                    sortOrder = Sort.ascending("createdAt");
                    break;
                case "dateDesc":
                    sortOrder = Sort.descending("createdAt");
                    break;
                case "amountAsc":
                    sortOrder = Sort.ascending("totalAmount");
                    break;
                case "amountDesc":
                    sortOrder = Sort.descending("totalAmount");
                    break;
                default:
                    break;
            }
        }

        PanacheQuery<OrderEntity> query;
        if (sortOrder != null) {
            query = OrderEntity.find(queryBuilder.toString(), sortOrder, params);
        } else {
            query = OrderEntity.find(queryBuilder.toString(), params);
        }

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 10;
        }
        query.page(Page.of(page - 1, limit));

        List<OrderEntity> orderEntityList = query.list();
        long totalItems = query.count();
        int totalPages = query.pageCount();

        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.setOrders(orderEntityList.stream().map(orderEntity -> {
            Order order = new Order();
            order.setOrderId(orderEntity.getId());
            order.setUserId(orderEntity.getUserId());
            order.setStatus(Order.StatusEnum.fromValue(orderEntity.getStatus()));
            order.setTotalAmount(orderEntity.getTotalAmount().floatValue());
            order.setCurrency(Order.CurrencyEnum.fromValue(orderEntity.getCurrency()));
            order.setCreatedAt(orderEntity.getCreatedAt());
            order.setUpdatedAt(orderEntity.getUpdatedAt());
            order.setItems(Mapper.mapToOrderItems(orderEntity.getOrderItems()));
            return order;
        }).collect(Collectors.toList()));

        OrderListResponsePagination pagination = new OrderListResponsePagination();
        pagination.setCurrentPage(page);
        pagination.setLimit(limit);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        orderListResponse.setPagination(pagination);

        return orderListResponse;
    }
}

