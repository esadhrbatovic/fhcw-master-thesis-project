package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.apigateway.clients.cart.api.ShoppingCartApi;
import com.hrbatovic.master.springboot.gateway.api.OrdersApi;
import com.hrbatovic.master.springboot.gateway.model.Order;
import com.hrbatovic.master.springboot.gateway.model.OrderListResponse;
import com.hrbatovic.springboot.master.apigateway.JwtAuthentication;
import com.hrbatovic.springboot.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.springboot.master.apigateway.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.hrbatovic.springboot.master.apigateway.JsonErrorParser.parseErrorMessage;

@RestController
public class OrderApiService implements OrdersApi {

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrderListResponse getAllOrders(Integer page, Integer limit, String status, String sort) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.order.api.OrdersApi ordersApiClient = new com.hrbatovic.master.springboot.apigateway.clients.order.api.OrdersApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                ordersApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                OrderListResponse response = mapper.map(ordersApiClient.getAllOrders(page, limit, status, sort));
                return response;
            }
            return null;
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Order getOrderById(UUID orderId) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.order.api.OrdersApi ordersApiClient = new com.hrbatovic.master.springboot.apigateway.clients.order.api.OrdersApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                ordersApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                Order response = mapper.map(ordersApiClient.getOrderById(orderId));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
