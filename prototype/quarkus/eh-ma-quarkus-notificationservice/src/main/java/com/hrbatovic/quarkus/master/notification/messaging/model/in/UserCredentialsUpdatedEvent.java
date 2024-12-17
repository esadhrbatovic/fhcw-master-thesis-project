package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class UserCredentialsUpdatedEvent implements Serializable {
    UUID id;
    String email;

    public UserCredentialsUpdatedEvent(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
