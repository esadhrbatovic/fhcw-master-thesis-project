package com.hrbatovic.micronaut.master.product.messaging.model.in;

import com.hrbatovic.micronaut.master.product.messaging.model.in.payload.LicenseTemplatePayload;
import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
public class LicenseTemplateUpdatedEvent implements Serializable {

    private LicenseTemplatePayload licenseTemplate;

    public LicenseTemplateUpdatedEvent() {
    }

    public LicenseTemplatePayload getLicenseTemplate() {
        return licenseTemplate;
    }

    public LicenseTemplateUpdatedEvent setLicenseTemplate(LicenseTemplatePayload licenseTemplate) {
        this.licenseTemplate = licenseTemplate;
        return this;
    }

    @Override
    public String toString() {
        return "LicenseTemplateUpdatedEvent{" +
                "licenseTemplate=" + licenseTemplate +
                '}';
    }
}
