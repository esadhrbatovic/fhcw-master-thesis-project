package com.hrbatovic.springboot.master.payment;

import com.hrbatovic.master.springboot.order.model.CreatePaymentMethodRequest;
import com.hrbatovic.master.springboot.order.model.UpdatePaymentMethodRequest;
import com.hrbatovic.springboot.master.payment.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {


    public static void validateCreatePaymentMethod(CreatePaymentMethodRequest createPaymentMethodRequest) {
        if (createPaymentMethodRequest == null ||
                createPaymentMethodRequest.getActive() == null ||
                StringUtils.isEmpty(createPaymentMethodRequest.getDescription()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getIconUrl()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getName()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getPaymentGatewayUrl()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getMerchantId()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getMerchantSecret()) ||
                StringUtils.isEmpty(createPaymentMethodRequest.getIconUrl())
        ) {
            throw new EhMaException(400, "Not all payment method information provided");
        }
    }

    public static void validatePaymentMethodId(UUID paymentMethodId) {
        if(paymentMethodId == null || StringUtils.isEmpty(paymentMethodId.toString())){
            throw new EhMaException(400, "Payment method id must not be empty");
        }
    }

    public static void validateUpdatePaymentMethod(UpdatePaymentMethodRequest updatePaymentMethodRequest) {
        if(updatePaymentMethodRequest == null || updatePaymentMethodRequest.getActive() == null ||
            StringUtils.isEmpty(updatePaymentMethodRequest.getDescription()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getName()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getSelector()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getPaymentGatewayUrl()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getMerchantId()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getIconUrl()) ||
                StringUtils.isEmpty(updatePaymentMethodRequest.getMerchantSecret())
        )
        {
            throw new EhMaException(400, "Not all update information provided.");
        }

    }
}
