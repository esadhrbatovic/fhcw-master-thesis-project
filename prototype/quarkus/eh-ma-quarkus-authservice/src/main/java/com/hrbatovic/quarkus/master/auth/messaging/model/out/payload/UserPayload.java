package com.hrbatovic.quarkus.master.auth.messaging.model.out.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class UserPayload implements Serializable {

    private UUID id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
    private AddressPayload address;

    public UserPayload() {
    }

    public UUID getId() {
        return id;
    }

    public UserPayload setId(UUID id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserPayload setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public AddressPayload getAddress() {
        return address;
    }

    public UserPayload setAddress(AddressPayload address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("role", role)
                .append("email", email)
                .append("phoneNumber", phoneNumber)
                .append("address", address)
                .toString();
    }
}
