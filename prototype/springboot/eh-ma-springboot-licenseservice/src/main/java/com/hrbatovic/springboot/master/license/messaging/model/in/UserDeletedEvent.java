package com.hrbatovic.springboot.master.license.messaging.model.in;

import java.io.Serializable;
import java.util.UUID;

public class UserDeletedEvent implements Serializable {

    public UserDeletedEvent() {
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    public UserDeletedEvent setId(UUID id) {
        this.id = id;
        return this;
    }
}
