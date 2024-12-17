package com.hrbatovic.quarkus.master.product.messaging.model.in;


import com.hrbatovic.quarkus.master.product.messaging.model.in.payload.LicenseTemplatePayload;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
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

