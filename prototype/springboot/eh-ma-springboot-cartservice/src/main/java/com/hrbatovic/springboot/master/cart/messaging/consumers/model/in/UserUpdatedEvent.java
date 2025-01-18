package com.hrbatovic.springboot.master.cart.messaging.consumers.model.in;

import com.hrbatovic.springboot.master.cart.messaging.consumers.model.in.payload.UserPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userPayload", userPayload)
                .toString();
    }
}
