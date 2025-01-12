package com.hrbatovic.micronaut.master.auth.messaging.model.in;

import com.hrbatovic.micronaut.master.auth.messaging.model.out.payload.UserPayload;
import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
public class UserUpdatedEvent implements Serializable {

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public UserUpdatedEvent setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
        return this;
    }
}