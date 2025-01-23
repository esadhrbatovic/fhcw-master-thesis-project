package com.hrbatovic.springboot.master.payment.api;

import com.hrbatovic.master.springboot.order.api.PaymentMethodsApi;
import com.hrbatovic.master.springboot.order.model.*;
import com.hrbatovic.springboot.master.payment.ApiInputValidator;
import com.hrbatovic.springboot.master.payment.ClaimUtils;
import com.hrbatovic.springboot.master.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.springboot.master.payment.db.repositories.PaymentMethodRepository;
import com.hrbatovic.springboot.master.payment.exceptions.EhMaException;
import com.hrbatovic.springboot.master.payment.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PaymentMethodsApiService implements PaymentMethodsApi {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        ApiInputValidator.validateCreatePaymentMethod(createPaymentMethodRequest);
        PaymentMethodEntity paymentMethodEntity = mapper.map(createPaymentMethodRequest);

        paymentMethodRepository.save(paymentMethodEntity);
        return mapper.toApi(paymentMethodEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DeletePaymentMethodResponse deletePaymentMethod(UUID id) {
        ApiInputValidator.validatePaymentMethodId(id);
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);
        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Paymentmethod not found.");
        }

        paymentMethodRepository.delete(paymentMethodEntity);

        return new DeletePaymentMethodResponse("Payment method deleted successfully.");
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        ApiInputValidator.validatePaymentMethodId(id);
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }

        return mapper.toApiDetail(paymentMethodEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public PaymentMethodListResponse getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findAll();

        if(claimUtils.getRoles().contains("ROLE_ADMIN") && !claimUtils.getRoles().contains("ROLE_CUSTOMER")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiList(paymentMethodEntities));
        }else if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiListNotAdmin(paymentMethodEntities));
        }

        return paymentMethodListResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID id, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        ApiInputValidator.validatePaymentMethodId(id);
        ApiInputValidator.validateUpdatePaymentMethod(updatePaymentMethodRequest);
        
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(id).orElse(null);

        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }
        mapper.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodRepository.save(paymentMethodEntity);

        return mapper.toApiDetail(paymentMethodEntity);
    }
}
