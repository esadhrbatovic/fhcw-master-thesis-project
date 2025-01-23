package com.hrbatovic.micronaut.master.payment.api;

import com.hrbatovic.micronaut.master.payment.ApiInputValidator;
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

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        ApiInputValidator.validateCreatePaymentMethod(createPaymentMethodRequest);
        PaymentMethodEntity paymentMethodEntity = mapper.map(createPaymentMethodRequest);

        paymentMethodRepository.save(paymentMethodEntity);
        return mapper.toApi(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id) {
        ApiInputValidator.validatePaymentMethodId(id);

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
        ApiInputValidator.validatePaymentMethodId(id);
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new RuntimeException("Payment method not found.");
        }

        return mapper.toApiDetail(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public PaymentMethodListResponse getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findAll();

        if(jwtUtil.getRoles().contains("admin") && !jwtUtil.getRoles().contains("customer")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiList(paymentMethodEntities));
        }else if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiListNotAdmin(paymentMethodEntities));
        }

        return paymentMethodListResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        ApiInputValidator.validatePaymentMethodId(id);
        ApiInputValidator.validateUpdatePaymentMethod(updatePaymentMethodRequest);
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new RuntimeException("Payment method not found.");
        }
        mapper.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodRepository.update(paymentMethodEntity);

        return mapper.toApiDetail(paymentMethodEntity);
    }
}
