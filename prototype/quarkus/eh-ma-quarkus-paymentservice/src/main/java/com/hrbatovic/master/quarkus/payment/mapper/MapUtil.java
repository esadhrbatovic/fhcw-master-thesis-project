package com.hrbatovic.master.quarkus.payment.mapper;

import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.UserPayload;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;
import com.hrbatovic.master.quarkus.payment.messaging.model.out.payload.PaymentPayload;
import com.hrbatovic.master.quarkus.payment.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract PaymentMethodEntity map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse toApi(PaymentMethodEntity paymentMethodEntity);

    public abstract PaymentMethodDetailedResponse toApiDetail(PaymentMethodEntity paymentMethodEntity);

    public abstract List<PaymentMethod> toApiList(List<PaymentMethodEntity> paymentMethodEntities);

    @Mapping(target="merchantId", ignore = true)
    public abstract List<PaymentMethod> toApiListNotAdmin(List<PaymentMethodEntity> paymentMethodEntities);

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
