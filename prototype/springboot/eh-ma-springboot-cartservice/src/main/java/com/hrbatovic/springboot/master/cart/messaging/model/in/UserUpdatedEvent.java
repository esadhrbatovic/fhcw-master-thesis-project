package com.hrbatovic.springboot.master.cart.messaging.model.in;

import com.hrbatovic.springboot.master.cart.messaging.model.in.payload.UserPayload;

import java.io.Serializable;

public class UserUpdatedEvent implements Serializable {
    private UserPayload userPayload;

    public UserUpdatedEvent() {
    }

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserUpdatedEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

}
