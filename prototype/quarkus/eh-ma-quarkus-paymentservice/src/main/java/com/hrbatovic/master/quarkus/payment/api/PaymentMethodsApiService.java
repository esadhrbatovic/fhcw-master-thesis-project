package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.exceptions.EhMaException;
import com.hrbatovic.master.quarkus.payment.mapper.MapUtil;
import com.hrbatovic.master.quarkus.payment.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class PaymentMethodsApiService implements PaymentMethodsApi {

    @Inject
    MapUtil mapper;

    @Override
    public Response createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = mapper.map(createPaymentMethodRequest);
        paymentMethodEntity.persistOrUpdate();
        return Response.ok(mapper.toApi(paymentMethodEntity)).status(200).build();
    }

    @Override
    public Response deletePaymentMethod(UUID paymentMethodId) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        paymentMethodEntity.delete();

        return Response.ok(new DeletePaymentMethodResponse().message("Payment method deleted successfully.")).status(200).build();
    }

    @Override
    public Response getPaymentMethodById(UUID id) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(id);
        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }

        return Response.ok(mapper.toApiDetail(paymentMethodEntity)).status(200).build();
    }

    //TODO: hide merchant id from customers
    @Override
    public Response getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = PaymentMethodEntity.listAll();
        paymentMethodListResponse.setPaymentMethods(mapper.toApiList(paymentMethodEntities));
        return Response.ok(paymentMethodListResponse).status(200).build();
    }

    @Override
    public Response updatePaymentMethod(UUID paymentMethodId, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }

        mapper.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodEntity.persistOrUpdate();
        return Response.ok(mapper.toApiDetail(paymentMethodEntity)).status(200).build();
    }
}
