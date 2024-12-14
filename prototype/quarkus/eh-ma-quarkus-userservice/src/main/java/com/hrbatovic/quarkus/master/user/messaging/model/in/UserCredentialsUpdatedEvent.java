package com.hrbatovic.quarkus.master.user.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserCredentialsUpdatedEvent implements Serializable {
    UUID id;
    String email;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
