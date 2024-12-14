package com.hrbatovic.quarkus.master.auth.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserUpdatedEvent implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String role;

    public UserUpdatedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}