package com.hrbatovic.micronaut.master.notification.mapper;

import com.hrbatovic.micronaut.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.micronaut.master.notification.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.notification.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.micronaut.master.notification.messaging.model.in.payload.UserPayload;
import com.hrbatovic.micronaut.master.notification.model.Notification;
import com.hrbatovic.micronaut.master.notification.model.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    public abstract UserEntity map(UserPayload userPayload);

    @Mapping(target = "email", ignore = true)
    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    public abstract void update(@MappingTarget UserEntity userEntity, UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);

    public abstract List<Notification> map(List<NotificationEntity> notificationEntityList);

    public abstract NotificationResponse map(NotificationEntity notificationEntity);

}
