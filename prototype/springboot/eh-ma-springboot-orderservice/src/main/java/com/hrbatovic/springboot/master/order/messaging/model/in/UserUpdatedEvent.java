package com.hrbatovic.springboot.master.order.messaging.model.in;

import com.hrbatovic.springboot.master.order.messaging.model.in.payload.UserPayload;

import java.io.Serializable;

public class UserUpdatedEvent implements Serializable {

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserUpdatedEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserUpdatedEvent{");
        sb.append("userPayload=").append(userPayload);
        sb.append('}');
        return sb.toString();
    }
}
