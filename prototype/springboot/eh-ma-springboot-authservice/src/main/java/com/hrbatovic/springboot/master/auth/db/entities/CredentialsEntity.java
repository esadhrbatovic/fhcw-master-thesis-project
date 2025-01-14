package com.hrbatovic.springboot.master.auth.db.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class CredentialsEntity {

    private UUID id;
    private String email;
    private String password;

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
