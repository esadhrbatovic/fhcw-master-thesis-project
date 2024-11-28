package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.OrdersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderListResponsePagination;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;

import java.util.*;
import java.util.stream.Collectors;

import com.hrbatovic.quarkus.master.order.mapper.Mapper;

@RequestScoped
public class OrderApiService implements OrdersApi {

    @Override
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            conditions.add("{ 'status': :status }");
            params.put("status", status);
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("{ '$and': [ ").append(String.join(", ", conditions)).append(" ] }");
        } else {
            queryBuilder.append("{}");
        }

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

    @Override
    public Order getOrderById(UUID orderId) {
        OrderEntity orderEntity = OrderEntity.findById(orderId);

        if(orderEntity == null){
            throw new RuntimeException("Order not found");
        }

        Order order = new Order();
        order.setStatus(Order.StatusEnum.fromString(orderEntity.getStatus()));
        order.setOrderId(orderEntity.getId());
        order.setCurrency(Order.CurrencyEnum.EUR);
        order.setCreatedAt(orderEntity.getCreatedAt());
        order.setUpdatedAt(orderEntity.getUpdatedAt());
        order.setItems(Mapper.mapToOrderItems(orderEntity.getOrderItems()));
        order.setUserId(orderEntity.getUserId());
        order.setTotalAmount(orderEntity.getTotalAmount().floatValue());

        return order;
    }
}
