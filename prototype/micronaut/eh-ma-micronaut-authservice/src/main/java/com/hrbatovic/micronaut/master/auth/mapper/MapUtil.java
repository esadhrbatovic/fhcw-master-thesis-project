package com.hrbatovic.micronaut.master.auth.mapper;

import com.hrbatovic.micronaut.master.auth.model.*;
import com.hrbatovic.micronaut.master.auth.db.entities.CredentialsEntity;
import com.hrbatovic.micronaut.master.auth.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.auth.messaging.model.out.payload.UserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    public abstract UserEntity map(UserForm userData);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract CredentialsEntity map(UserUpdateCredentialsRequest credentials);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract CredentialsEntity map(AdminUpdateCredentialsRequest updateCredentialsRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract CredentialsEntity map(CredentialsForm credentials);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "userData.firstName")
    @Mapping(target = "lastName", source = "userData.lastName")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", source = "credentials.email")
    @Mapping(target = "phoneNumber", source = "userData.phoneNumber")
    @Mapping(target = "address", source = "userData.address")
    public abstract UserPayload map(RegisterRequest registerRequest);

    @Mapping(target="id", ignore = true)
    public abstract void update(UserPayload userPayload, @MappingTarget UserEntity userEntity);

}
