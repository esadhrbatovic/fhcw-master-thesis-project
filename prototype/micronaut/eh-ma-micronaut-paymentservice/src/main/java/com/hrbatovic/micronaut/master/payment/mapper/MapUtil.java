package com.hrbatovic.micronaut.master.payment.mapper;

import com.hrbatovic.micronaut.master.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.micronaut.master.payment.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.payment.messaging.model.in.payload.OrderPayload;
import com.hrbatovic.micronaut.master.payment.messaging.model.in.payload.UserPayload;
import com.hrbatovic.micronaut.master.payment.messaging.model.out.payload.PaymentPayload;
import com.hrbatovic.micronaut.master.payment.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract PaymentMethodEntity map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse toApi(PaymentMethodEntity paymentMethodEntity);

    public abstract PaymentMethodDetailedResponse toApiDetail(PaymentMethodEntity paymentMethodEntity);

    public List<PaymentMethod> toApiList(List<PaymentMethodEntity> paymentMethodEntities) {
        if (paymentMethodEntities == null) {
            return null;
        }

        List<PaymentMethod> list = new ArrayList<>(paymentMethodEntities.size());
        for (PaymentMethodEntity entity : paymentMethodEntities) {
            list.add(toApiPaymentMethod(entity));
        }
        return list;
    }

    public abstract PaymentMethod toApiPaymentMethod(PaymentMethodEntity paymentMethodEntity);


    public List<PaymentMethod> toApiListNotAdmin(List<PaymentMethodEntity> paymentMethodEntities) {
        if (paymentMethodEntities == null) {
            return null;
        }

        List<PaymentMethod> list = new ArrayList<>(paymentMethodEntities.size());
        for (PaymentMethodEntity entity : paymentMethodEntities) {
            list.add(toApiNotAdmin(entity));
        }
        return list;
    }

    @Mapping(target = "merchantId", ignore = true)
    protected abstract PaymentMethod toApiNotAdmin(PaymentMethodEntity paymentMethodEntity);

    @Mapping(target="id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract void patch(UpdatePaymentMethodRequest updatePaymentMethodRequest, @MappingTarget PaymentMethodEntity paymentMethodEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "paidItemPayloads", source = "orderItems")
    public abstract PaymentPayload map(OrderPayload orderEntity);
}
