package com.hrbatovic.springboot.master.user.messaging.model.in;

import com.hrbatovic.springboot.master.user.messaging.model.common.payload.UserPayload;

import java.io.Serializable;

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
