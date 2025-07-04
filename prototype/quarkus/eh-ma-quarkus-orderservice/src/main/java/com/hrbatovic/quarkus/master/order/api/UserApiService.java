package com.hrbatovic.quarkus.master.order.api;

import com.hrbatovic.master.quarkus.order.api.UsersApi;
import com.hrbatovic.master.quarkus.order.model.OrderListResponse;
import com.hrbatovic.master.quarkus.order.model.OrderListResponsePagination;
import com.hrbatovic.quarkus.master.order.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.order.db.entities.OrderEntity;
import com.hrbatovic.quarkus.master.order.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.order.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.*;

@RequestScoped
public class UserApiService implements UsersApi {

    @Inject
    MapUtil mapper;

    @Inject
    @Claim(standard = Claims.groups)
    Set<String> groupsClaim;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Override
    @RolesAllowed({"admin", "customer"})
    public OrderListResponse getUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {
        ApiInputValidator.validateUserId(userId);

        if(groupsClaim.contains("customer") && !groupsClaim.contains("admin") ){
            if(!userId.equals(UUID.fromString(userSubClaim))){
                throw new EhMaException(400, "You are not allowed to view other user's orders.");
            }
        }


        PanacheQuery<OrderEntity> query = OrderEntity.queryUserOrders(userId, status, sort);

        Page pagination = Page.of(
                (page != null && page > 0) ? page - 1 : 0,
                (limit != null && limit > 0) ? limit : 10
        );
        query.page(pagination);

        return createOrderListResponse(query.list(), query.pageCount(), query.count(), page, limit);
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

