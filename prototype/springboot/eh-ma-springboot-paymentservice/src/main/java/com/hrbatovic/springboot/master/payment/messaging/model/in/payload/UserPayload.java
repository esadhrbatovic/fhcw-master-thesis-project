package com.hrbatovic.springboot.master.payment.messaging.model.in.payload;


import java.io.Serializable;
import java.util.UUID;

public class UserPayload implements Serializable {

    private UUID id;
    private String role;

    public UserPayload() {
    }

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

}
