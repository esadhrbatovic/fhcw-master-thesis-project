package com.hrbatovic.springboot.master.product.messaging.model.in;

import com.hrbatovic.springboot.master.product.messaging.model.in.payload.UserPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
