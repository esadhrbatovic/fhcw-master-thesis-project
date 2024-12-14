package com.hrbatovic.quarkus.master.notification.mapper;

import com.hrbatovic.quarkus.master.notification.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.notification.messaging.model.in.UserRegisteredEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {


    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);
}
