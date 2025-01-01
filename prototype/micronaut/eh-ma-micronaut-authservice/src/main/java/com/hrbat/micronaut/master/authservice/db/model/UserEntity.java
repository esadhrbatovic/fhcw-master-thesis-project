package com.hrbat.micronaut.master.authservice.db.model;

import io.micronaut.data.annotation.Id;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class UserEntity {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String role;

    public UserEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}