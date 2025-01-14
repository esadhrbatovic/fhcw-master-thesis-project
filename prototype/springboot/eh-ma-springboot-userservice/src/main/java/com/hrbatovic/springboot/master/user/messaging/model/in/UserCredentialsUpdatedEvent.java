package com.hrbatovic.springboot.master.user.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserCredentialsUpdatedEvent implements Serializable {

    private UUID id;
    private String email;

    public UUID getId() {
        return id;
    }

    public UserCredentialsUpdatedEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserCredentialsUpdatedEvent setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentialsUpdatedEvent() {
    }
}
