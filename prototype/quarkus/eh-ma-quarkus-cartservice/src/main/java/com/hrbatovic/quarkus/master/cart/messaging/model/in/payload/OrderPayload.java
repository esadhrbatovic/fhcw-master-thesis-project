package com.hrbatovic.quarkus.master.cart.messaging.model.in.payload;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RegisterForReflection
public class OrderPayload implements Serializable {

    private UUID id;

    public UUID getId() {
        return id;
    }

}
