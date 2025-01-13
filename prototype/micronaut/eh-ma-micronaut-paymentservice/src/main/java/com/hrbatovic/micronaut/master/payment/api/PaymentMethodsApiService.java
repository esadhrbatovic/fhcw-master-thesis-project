package com.hrbatovic.micronaut.master.payment.api;

import com.hrbatovic.micronaut.master.payment.JwtUtil;
import com.hrbatovic.micronaut.master.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.micronaut.master.payment.db.repositories.PaymentMethodRepository;
import com.hrbatovic.micronaut.master.payment.mapper.MapUtil;
import com.hrbatovic.micronaut.master.payment.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class PaymentMethodsApiService implements PaymentMethodsApi{

    @Inject
    PaymentMethodRepository paymentMethodRepository;

    @Inject
    JwtUtil jwtUtil;

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = MapUtil.INSTANCE.map(createPaymentMethodRequest);

        paymentMethodRepository.save(paymentMethodEntity);
        return MapUtil.INSTANCE.toApi(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);
        if(paymentMethodEntity == null){
            throw new RuntimeException("Paymentmethod not found.");
        }

        paymentMethodRepository.delete(paymentMethodEntity);

        return new DeletePaymentMethodResponse("Payment method deleted successfully.");
    }

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new RuntimeException("Payment method not found.");
        }

        return MapUtil.INSTANCE.toApiDetail(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public PaymentMethodListResponse getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findAll();

        if(jwtUtil.getRoles().contains("admin") && !jwtUtil.getRoles().contains("customer")){
            paymentMethodListResponse.setPaymentMethods(MapUtil.INSTANCE.toApiList(paymentMethodEntities));
        }else if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            paymentMethodListResponse.setPaymentMethods(MapUtil.INSTANCE.toApiListNotAdmin(paymentMethodEntities));
        }

        return paymentMethodListResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new RuntimeException("Payment method not found.");
        }
        MapUtil.INSTANCE.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodRepository.update(paymentMethodEntity);

        return MapUtil.INSTANCE.toApiDetail(paymentMethodEntity);
    }
}
