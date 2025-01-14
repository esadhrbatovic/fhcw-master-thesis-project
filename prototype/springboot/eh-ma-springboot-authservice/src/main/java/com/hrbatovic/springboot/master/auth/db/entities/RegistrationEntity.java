package com.hrbatovic.springboot.master.auth.db.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "registrations")
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