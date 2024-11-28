package com.hrbatovic.quarkus.master.order.messaging.model;

import java.io.Serializable;
import java.util.UUID;

public class UserUpdatedMsgPayload implements Serializable {
    private UUID id;
    private String role;

    public UserUpdatedMsgPayload() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserUpdatedMsgPayload{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
