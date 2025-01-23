package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.PaymentMethodsApi;
import com.hrbatovic.micronaut.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
public class PaymentApiService implements PaymentMethodsApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.payment.api.PaymentMethodsApi paymentMethodsApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public PaymentMethodResponse createPaymentMethod(String authorization, CreatePaymentMethodRequest createPaymentMethodRequest) {
        try {
            return mapper.map(paymentMethodsApiClient.createPaymentMethod(authorization, mapper.map(createPaymentMethodRequest)));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id, String authorization) {
        try {
            return mapper.map(paymentMethodsApiClient.deletePaymentMethod(id, authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id, String authorization) {
        try {
            return mapper.map(paymentMethodsApiClient.getPaymentMethodById(id, authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public PaymentMethodListResponse getPaymentMethods(String authorization) {
        try {
            return mapper.map(paymentMethodsApiClient.getPaymentMethods(authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, String authorization, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        try {
            return mapper.map(paymentMethodsApiClient.updatePaymentMethod(id, authorization, mapper.map(updatePaymentMethodRequest)));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }
}
