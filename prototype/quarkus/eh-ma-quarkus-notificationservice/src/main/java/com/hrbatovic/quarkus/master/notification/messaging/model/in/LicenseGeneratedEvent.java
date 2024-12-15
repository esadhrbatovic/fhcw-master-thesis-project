package com.hrbatovic.quarkus.master.notification.messaging.model.in;

import com.hrbatovic.quarkus.master.notification.messaging.model.in.payload.LicensePayload;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class LicenseGeneratedEvent implements Serializable {
    private UUID orderId;
    private UUID userId;

    private List<LicensePayload> licenses;

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

    public List<LicensePayload> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<LicensePayload> licenses) {
        this.licenses = licenses;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", orderId)
                .append("userId", userId)
                .append("licenses", licenses)
                .toString();
    }
}
