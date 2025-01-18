package com.hrbatovic.springboot.master.notification.mapper;


import com.hrbatovic.master.springboot.notification.model.Notification;
import com.hrbatovic.master.springboot.notification.model.NotificationResponse;
import com.hrbatovic.springboot.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.springboot.master.notification.db.entities.UserEntity;
import com.hrbatovic.springboot.master.notification.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.springboot.master.notification.messaging.model.in.payload.UserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
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
