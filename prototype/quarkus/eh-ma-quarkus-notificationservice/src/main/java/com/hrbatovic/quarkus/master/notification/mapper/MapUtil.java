package com.hrbatovic.quarkus.master.notification.mapper;

import com.hrbatovic.quarkus.master.notification.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserUpdatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);

    public abstract void update(@MappingTarget UserEntity userEntity, UserUpdatedEvent userUpdatedEvent);

    public abstract void update(@MappingTarget UserEntity userEntity, UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);
}
