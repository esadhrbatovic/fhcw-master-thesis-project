package com.hrbatovic.micronaut.master.user.mapper;

import com.hrbatovic.micronaut.master.user.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.user.messaging.model.common.payload.UserPayload;
import com.hrbatovic.micronaut.master.user.messaging.model.in.UserCredentialsUpdatedEvent;
import com.hrbatovic.micronaut.master.user.model.UpdateUserProfileRequest;
import com.hrbatovic.micronaut.master.user.model.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    public abstract UserProfileResponse map(UserEntity userEntity);

    public abstract List<UserProfileResponse> map(List<UserEntity> userEntityList);

    public abstract UserPayload mapFromEntity(UserEntity userEntity);

    @Mapping(target="id", ignore = true)
    @Mapping(target="email", ignore = true)
    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    public abstract void update(@MappingTarget UserEntity userEntity, UpdateUserProfileRequest updateUserProfileRequest);

    @Mapping(target="id", ignore = true)
    @Mapping(target="email", ignore = true)
    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    @Mapping(target="role", ignore = true)
    public abstract void updateNotAdmin(@MappingTarget UserEntity userEntity, UpdateUserProfileRequest updateUserProfileRequest);

    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    public abstract UserEntity map(UserPayload userPayload);

    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    @Mapping(target="firstName", ignore = true)
    @Mapping(target="lastName", ignore = true)
    @Mapping(target="role", ignore = true)
    @Mapping(target="phoneNumber", ignore = true)
    @Mapping(target="address", ignore = true)
    public abstract UserEntity update(@MappingTarget UserEntity userEntity, UserCredentialsUpdatedEvent userCredentialsUpdatedEvent);

}
