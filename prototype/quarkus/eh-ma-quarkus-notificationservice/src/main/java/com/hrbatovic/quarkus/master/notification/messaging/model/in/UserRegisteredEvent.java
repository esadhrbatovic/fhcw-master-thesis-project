package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserRegisteredEvent implements Serializable {

    private UUID id;

    private String role;

    private String email;

    private String firstName;

    private String lastName;

    public UserRegisteredEvent() {
    }

    public UUID getId() {
        return id;
    }

    public UserRegisteredEvent setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserRegisteredEvent setRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisteredEvent setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisteredEvent setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisteredEvent setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
