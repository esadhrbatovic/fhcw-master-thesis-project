package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserRegisteredEvent implements Serializable {

    private UUID id;

    private String role;

    private String email;

    public UserRegisteredEvent() {
    }

    public UUID getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
