package com.hrbatovic.quarkus.master.user.mapper;

import com.hrbatovic.master.quarkus.user.model.UpdateUserProfileRequest;
import com.hrbatovic.master.quarkus.user.model.UserProfileResponse;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.quarkus.master.user.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.quarkus.master.user.messaging.model.out.UserUpdatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {


    public abstract UserProfileResponse map(UserEntity userEntity);

    public abstract List<UserProfileResponse> map(List<UserEntity> userEntityList);

    @Mapping(target="id", ignore = true)
    public abstract UserUpdatedEvent map(UpdateUserProfileRequest updateUserProfileRequest);

    @Mapping(target="id", ignore = true)
    @Mapping(target="email", ignore = true)
    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    public abstract void update(@MappingTarget UserEntity userEntity, UpdateUserProfileRequest updateUserProfileRequest);

    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);

    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    public abstract UserEntity update(@MappingTarget UserEntity userEntity, UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);
}
