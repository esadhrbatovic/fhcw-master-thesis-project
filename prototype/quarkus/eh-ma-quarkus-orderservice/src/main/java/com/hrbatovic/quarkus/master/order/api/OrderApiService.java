package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.OrdersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.Order;
import com.hrbatovic.master.quarkus.order.model.OrderListResponsePagination;
import com.hrbatovic.quarkus.master.order.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.order.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.*;

@RequestScoped
public class OrderApiService implements OrdersApi {

    @Inject
    MapUtil mapper;

    @Override
    public Response getAllOrders(Integer page, Integer limit, String status, String sort) {
        PanacheQuery<OrderEntity> query = OrderEntity.queryOrders(status, sort);

        Page pagination = Page.of(
                (page != null && page > 0) ? page - 1 : 0,
                (limit != null && limit > 0) ? limit : 10
        );

        query.page(pagination);

        return Response.ok(createOrderListResponse(query.list(), query.pageCount(), query.count(), page, limit)).status(200).build();
    }

    //TODO: admin only
    @Override
    public Response getOrderById(UUID orderId) {
        ApiInputValidator.validateOrderId(orderId);

        OrderEntity orderEntity = OrderEntity.findById(orderId);

        if(orderEntity == null){
            throw new EhMaException(404, "Order not found");
        }

        return Response.ok(mapper.map(orderEntity)).status(200).build();
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
