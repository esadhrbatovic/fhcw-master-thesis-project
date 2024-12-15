package com.hrbatovic.quarkus.master.user.messaging.model.in;

import com.hrbatovic.quarkus.master.user.messaging.model.common.payload.UserPayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class UserRegisteredEvent implements Serializable {

    private UserPayload userPayload;

    public UserPayload getUserPayload() {
        return userPayload;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("user", userPayload)
                .toString();
    }
}
