package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.UserPayload;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRegisteredEvent{");
        sb.append("userPayload=").append(userPayload);
        sb.append('}');
        return sb.toString();
    }
}
