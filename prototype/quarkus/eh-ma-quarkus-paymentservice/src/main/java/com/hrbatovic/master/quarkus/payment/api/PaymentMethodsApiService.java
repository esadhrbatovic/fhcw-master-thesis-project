package com.hrbatovic.master.quarkus.payment.api;

import com.hrbatovic.master.quarkus.payment.api.validators.ApiInputValidator;
import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.exceptions.EhMaException;
import com.hrbatovic.master.quarkus.payment.mapper.MapUtil;
import com.hrbatovic.master.quarkus.payment.model.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestScoped
public class PaymentMethodsApiService implements PaymentMethodsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @Claim(standard = Claims.groups)
    Set<String> groupsClaim;

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodResponse createPaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        ApiInputValidator.validateCreatePaymentMethod(createPaymentMethodRequest);

        PaymentMethodEntity paymentMethodEntity = mapper.map(createPaymentMethodRequest);
        paymentMethodEntity.persistOrUpdate();
        return mapper.toApi(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public DeletePaymentMethodResponse deletePaymentMethod(UUID paymentMethodId) {
        ApiInputValidator.validatePaymentMethodId(paymentMethodId);

        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        paymentMethodEntity.delete();

        return new DeletePaymentMethodResponse().message("Payment method deleted successfully.");
    }

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse getPaymentMethodById(UUID id) {
        ApiInputValidator.validatePaymentMethodId(id);

        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(id);
        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }

        return mapper.toApiDetail(paymentMethodEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public PaymentMethodListResponse getPaymentMethods() {
        PaymentMethodListResponse paymentMethodListResponse = new PaymentMethodListResponse();
        List<PaymentMethodEntity> paymentMethodEntities = PaymentMethodEntity.listAll();
        if(groupsClaim.contains("admin") && !groupsClaim.contains("customer")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiList(paymentMethodEntities));
        }else if(groupsClaim.contains("customer") && !groupsClaim.contains("admin")){
            paymentMethodListResponse.setPaymentMethods(mapper.toApiListNotAdmin(paymentMethodEntities));
        }

        return paymentMethodListResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public PaymentMethodDetailedResponse updatePaymentMethod(UUID paymentMethodId, UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        ApiInputValidator.validatePaymentMethodId(paymentMethodId);
        ApiInputValidator.validateUpdatePaymentMethod(updatePaymentMethodRequest);

        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.findById(paymentMethodId);
        if(paymentMethodEntity == null){
            throw new EhMaException(404, "Payment method not found.");
        }

        mapper.patch(updatePaymentMethodRequest, paymentMethodEntity);
        paymentMethodEntity.persistOrUpdate();
        return mapper.toApiDetail(paymentMethodEntity);
    }
}
