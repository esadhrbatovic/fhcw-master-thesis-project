package com.hrbatovic.quarkus.master.cart.messaging.model.in.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderPayload implements Serializable {

    private UUID id;

    public UUID getId() {
        return id;
    }

}
