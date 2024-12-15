package com.hrbatovic.master.quarkus.payment.mapper;

import com.hrbatovic.master.quarkus.payment.db.entities.PaymentMethodEntity;
import com.hrbatovic.master.quarkus.payment.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.UserPayload;
import com.hrbatovic.master.quarkus.payment.messaging.model.in.payload.OrderPayload;
import com.hrbatovic.master.quarkus.payment.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {


    @Mapping(target = "id", ignore = true)
    public abstract PaymentMethodEntity map(CreatePaymentMethodRequest createPaymentMethodRequest);

    public abstract PaymentMethodResponse toApi(PaymentMethodEntity paymentMethodEntity);

    public abstract PaymentMethodDetailedResponse toApiDetail(PaymentMethodEntity paymentMethodEntity);

    public abstract List<PaymentMethod> toApiList(List<PaymentMethodEntity> paymentMethodEntities);

    @Mapping(target="id", ignore = true)
    public abstract void patch(UpdatePaymentMethodRequest updatePaymentMethodRequest, @MappingTarget PaymentMethodEntity paymentMethodEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    public abstract OrderPayload map(OrderPayload orderEntity);
}
