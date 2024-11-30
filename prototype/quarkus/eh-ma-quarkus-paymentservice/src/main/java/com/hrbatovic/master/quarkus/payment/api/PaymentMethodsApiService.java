package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.mapper.MapUtil;
import com.hrbatovic.master.quarkus.payment.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class PaymentMethodsApiService implements PaymentMethodsApi {


    @Inject
    MapUtil mapping;

    @Override
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = mapping.map(createPaymentMethodRequest);
        paymentMethodEntity.persistOrUpdate();
        return mapping.toApi(paymentMethodEntity);
    }

    @Override
    public DeletePaymentMethodResponse deletePaymentMethod(UUID paymentMethodId) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        paymentMethodEntity.delete();

        return new DeletePaymentMethodResponse().message("Payment method deleted successfully.");
    }

    @Override
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(id);
        //TODO: error handling

        return mapping.toApiDetail(paymentMethodEntity);
    }

    @Override
    public PaymentMethodListResponse getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = PaymentMethodEntity.listAll();
        paymentMethodListResponse.setPaymentMethods(mapping.toApiList(paymentMethodEntities));
        return paymentMethodListResponse;
    }

    @Override
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID paymentMethodId, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        //TODO : error handling

        mapping.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodEntity.persistOrUpdate();
        return mapping.toApiDetail(paymentMethodEntity);
    }
}
