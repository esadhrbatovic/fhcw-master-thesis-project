package com.hrbat.micronaut.master.authservice.db.model;

import io.micronaut.data.annotation.Id;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class CredentialsEntity {

    @Id
    private UUID id;
    private String email;
    private String password;

    //TODO: save without id
    public CredentialsEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public CredentialsEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}