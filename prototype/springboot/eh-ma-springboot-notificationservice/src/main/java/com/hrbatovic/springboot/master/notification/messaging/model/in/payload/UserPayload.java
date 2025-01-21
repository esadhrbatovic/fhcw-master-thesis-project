package com.hrbatovic.springboot.master.notification.messaging.model.in.payload;

import java.io.Serializable;
import java.util.UUID;

public class UserPayload implements Serializable {

    public UserPayload() {
    }

    private UUID id;

    private String role;

    private String email;

    private String firstName;

    private String lastName;

    public UUID getId() {
        return id;
    }

    public UserPayload setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserPayload setRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserPayload setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserPayload setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserPayload setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


}
