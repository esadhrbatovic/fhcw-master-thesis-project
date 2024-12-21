package com.hrbatovic.quarkus.master.order.api.validators;

import com.hrbatovic.quarkus.master.order.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {

    public static void validateOrderId(UUID orderId) {
        if(orderId == null || StringUtils.isEmpty(orderId.toString())){
            throw new EhMaException(400, "Order id must not be null");
        }
    }

    public static void validateUserId(UUID userId) {
        if(userId == null || StringUtils.isEmpty(userId.toString())){
            throw new EhMaException(400, "User id must not be null");
        }
    }
}
