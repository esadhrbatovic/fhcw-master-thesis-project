package com.hrbatovic.micronaut.master.auth.db.entities;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@MappedEntity(value = "registrations")
@Serdeable
public class RegistrationEntity {

    @Id
    private UUID id;

    private UserEntity userEntity;
    private CredentialsEntity credentialsEntity;


    public RegistrationEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CredentialsEntity getCredentialsEntity() {
        return credentialsEntity;
    }

    public void setCredentialsEntity(CredentialsEntity credentialsEntity) {
        this.credentialsEntity = credentialsEntity;
    }
}
