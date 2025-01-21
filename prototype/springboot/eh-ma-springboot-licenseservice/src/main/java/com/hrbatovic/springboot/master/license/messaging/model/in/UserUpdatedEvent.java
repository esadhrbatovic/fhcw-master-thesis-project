package com.hrbatovic.springboot.master.license.messaging.model.in;

import com.hrbatovic.springboot.master.license.messaging.model.in.payload.UserPayload;


import java.io.Serializable;

public class UserUpdatedEvent implements Serializable {

    public UserUpdatedEvent() {
    }

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserUpdatedEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

}
