package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.api.PaymentMethodsApi;
import com.hrbatovic.master.quarkus.payment.model.*;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class PaymentMethodsApiService implements PaymentMethodsApi {

    @Override
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        return null;
    }

    @Override
    public DeletePaymentMethodResponse deletePaymentMethod(UUID paymentMethodId) {
        return null;
    }

    @Override
    public PaymentMethodListResponse getPaymentMethods() {
        return null;
    }

    @Override
    public PaymentMethodResponse updatePaymentMethod(UUID paymentMethodId, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        return null;
    }
}
