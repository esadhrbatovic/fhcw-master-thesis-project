package com.hrbatovic.master.quarkus.payment.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserUpdatedEvent implements Serializable {
    private UUID id;
    private String role;

    public UUID getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
