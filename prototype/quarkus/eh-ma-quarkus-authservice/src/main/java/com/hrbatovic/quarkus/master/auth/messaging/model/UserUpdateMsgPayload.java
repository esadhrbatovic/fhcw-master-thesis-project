package com.hrbatovic.quarkus.master.auth.messaging.model;

import java.io.Serializable;
import java.util.UUID;

public class UserUpdateMsgPayload implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String password;
    private String phoneNumber;
    private AddressPayload address;

    public UserUpdateMsgPayload() {
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

    public AddressPayload getAddress() {
        return address;
    }

    public void setAddress(AddressPayload address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}