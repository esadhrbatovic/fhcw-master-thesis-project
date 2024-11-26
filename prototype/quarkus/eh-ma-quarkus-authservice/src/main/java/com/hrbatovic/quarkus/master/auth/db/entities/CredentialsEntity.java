package com.hrbatovic.quarkus.master.auth.db.entities;

public class CredentialsEntity extends AuthBaseEntity {

    private String email;
    private String password;

    public CredentialsEntity() {
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