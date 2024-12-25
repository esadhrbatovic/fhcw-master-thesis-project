package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.PaymentMethodsApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.payment.api.PaymentMethodsPaymentRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class PaymentApiService implements PaymentMethodsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    PaymentMethodsPaymentRestClient paymentMethodsPaymentRestClient;


    @Override
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        return mapper.map(paymentMethodsPaymentRestClient.createPaymentMethod(mapper.map(createPaymentMethodRequest)));
    }

    @Override
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id) {
        return mapper.map(paymentMethodsPaymentRestClient.deletePaymentMethod(id));
    }

    @Override
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        return mapper.map(paymentMethodsPaymentRestClient.getPaymentMethodById(id));
    }

    @Override
    public PaymentMethodListResponse getPaymentMethods() {
        return mapper.map(paymentMethodsPaymentRestClient.getPaymentMethods());
    }

    @Override
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        return mapper.map(paymentMethodsPaymentRestClient.updatePaymentMethod(id, mapper.map(updatePaymentMethodRequest)));
    }
}
