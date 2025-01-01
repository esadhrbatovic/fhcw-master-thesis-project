package com.hrbat.micronaut.master.userservice;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
//TODO: NOTE - GETTER AND SETTER NEEDED HERE
public class UserRegisteredEvent implements Serializable {

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserRegisteredEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

    public UserRegisteredEvent() {
    }
}
