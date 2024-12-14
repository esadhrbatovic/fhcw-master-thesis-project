package com.hrbatovic.quarkus.master.auth.mapper;

import com.hrbatovic.master.quarkus.auth.model.*;
import com.hrbatovic.quarkus.master.auth.db.entities.CredentialsEntity;
import com.hrbatovic.quarkus.master.auth.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.auth.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.quarkus.master.auth.messaging.model.out.UserRegisteredEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
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
    public abstract UserRegisteredEvent map(RegisterRequest registerRequest);

    @Mapping(target="id", ignore = true)
    public abstract void update(UserUpdatedEvent userUpdatedEvent, @MappingTarget UserEntity userEntity);

}
