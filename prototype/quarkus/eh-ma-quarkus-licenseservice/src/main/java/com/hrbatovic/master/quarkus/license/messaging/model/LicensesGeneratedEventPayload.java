package com.hrbatovic.master.quarkus.license.messaging.model;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class LicensesGeneratedEventPayload implements Serializable {
    private UUID orderId;
    private UUID userId;
    private List<LicenseEntity> licenses;

    public LicensesGeneratedEventPayload() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<LicenseEntity> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<LicenseEntity> licenses) {
        this.licenses = licenses;
    }

    @Override
    public String toString() {
        return "LicensesGeneratedEventPayload{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", licenses=" + licenses +
                '}';
    }
}
