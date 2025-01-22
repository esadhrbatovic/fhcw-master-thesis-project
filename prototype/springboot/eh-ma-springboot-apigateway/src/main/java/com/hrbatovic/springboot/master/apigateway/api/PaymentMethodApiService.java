package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.gateway.api.PaymentMethodsApi;
import com.hrbatovic.master.springboot.gateway.model.*;
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
public class PaymentMethodApiService implements PaymentMethodsApi {

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                paymentMethodsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                PaymentMethodResponse response = mapper.map(paymentMethodsApiClient.createPaymentMethod(mapper.map(createPaymentMethodRequest)));
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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                paymentMethodsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                DeletePaymentMethodResponse response = mapper.map(paymentMethodsApiClient.deletePaymentMethod(id));
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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                paymentMethodsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                PaymentMethodDetailedResponse response = mapper.map(paymentMethodsApiClient.getPaymentMethodById(id));
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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public PaymentMethodListResponse getPaymentMethods() {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                paymentMethodsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                PaymentMethodListResponse response = mapper.map(paymentMethodsApiClient.getPaymentMethods());
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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.payment.api.PaymentMethodsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                paymentMethodsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                PaymentMethodDetailedResponse response = mapper.map(paymentMethodsApiClient.updatePaymentMethod(id, mapper.map(updatePaymentMethodRequest)));
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
}
