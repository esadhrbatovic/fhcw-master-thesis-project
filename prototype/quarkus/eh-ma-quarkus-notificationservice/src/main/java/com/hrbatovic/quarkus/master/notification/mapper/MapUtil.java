package com.hrbatovic.quarkus.master.notification.mapper;

import com.hrbatovic.quarkus.master.notification.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.payload.UserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);
}
