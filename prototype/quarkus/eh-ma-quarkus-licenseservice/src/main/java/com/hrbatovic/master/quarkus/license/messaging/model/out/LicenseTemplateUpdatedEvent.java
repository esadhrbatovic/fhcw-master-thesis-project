package com.hrbatovic.master.quarkus.license.messaging.model.out;

import com.hrbatovic.master.quarkus.license.messaging.model.out.payload.LicenseTemplatePayload;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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

