package com.hrbatovic.springboot.master.payment.messaging.model.in;

import com.hrbatovic.springboot.master.payment.messaging.model.in.payload.UserPayload;
import java.io.Serializable;

public class UserRegisteredEvent implements Serializable {

    public UserRegisteredEvent() {
    }

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserRegisteredEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

}
