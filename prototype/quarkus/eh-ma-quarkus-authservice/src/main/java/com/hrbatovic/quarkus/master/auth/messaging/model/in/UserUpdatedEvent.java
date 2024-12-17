package com.hrbatovic.quarkus.master.auth.messaging.model.in;

import com.hrbatovic.quarkus.master.auth.messaging.model.out.payload.UserPayload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

@RegisterForReflection
public class UserUpdatedEvent implements Serializable {

    private UserPayload user;

    public UserPayload getUser() {
        return user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("user", user)

                .toString();
    }
}