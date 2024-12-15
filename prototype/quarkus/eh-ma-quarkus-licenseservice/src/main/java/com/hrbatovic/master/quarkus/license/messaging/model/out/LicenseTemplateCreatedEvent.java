package com.hrbatovic.master.quarkus.license.messaging.model.out;

import com.hrbatovic.master.quarkus.license.messaging.model.out.payload.LicenseTemplatePayload;

import java.io.Serializable;

public class LicenseTemplateCreatedEvent implements Serializable {

    private LicenseTemplatePayload licenseTemplate;

    public LicenseTemplateCreatedEvent() {
    }

    public LicenseTemplatePayload getLicenseTemplate() {
        return licenseTemplate;
    }

    public LicenseTemplateCreatedEvent setLicenseTemplate(LicenseTemplatePayload licenseTemplate) {
        this.licenseTemplate = licenseTemplate;
        return this;
    }

    @Override
    public String toString() {
        return "LicenseTemplateCreatedEvent{" +
                "licenseTemplate=" + licenseTemplate +
                '}';
    }
}
