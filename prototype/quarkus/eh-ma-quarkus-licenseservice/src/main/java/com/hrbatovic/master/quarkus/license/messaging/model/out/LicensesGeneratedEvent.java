package com.hrbatovic.master.quarkus.license.messaging.model.out;

import com.hrbatovic.master.quarkus.license.messaging.model.out.payload.LicensePayload;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class LicensesGeneratedEvent implements Serializable {
    private UUID orderId;
    private UUID userId;
    private List<LicensePayload> licenses;

    public LicensesGeneratedEvent() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LicensesGeneratedEvent setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public LicensesGeneratedEvent setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public List<LicensePayload> getLicenses() {
        return licenses;
    }

    public LicensesGeneratedEvent setLicenses(List<LicensePayload> licenses) {
        this.licenses = licenses;
        return this;
    }

    @Override
    public String toString() {
        return "LicensesGeneratedEvent{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", licenses=" + licenses +
                '}';
    }
}
