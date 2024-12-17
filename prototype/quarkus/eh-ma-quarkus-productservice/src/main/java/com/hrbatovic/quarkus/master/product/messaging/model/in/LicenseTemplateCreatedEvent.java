package com.hrbatovic.quarkus.master.product.messaging.model.in;


import com.hrbatovic.quarkus.master.product.messaging.model.in.payload.LicenseTemplatePayload;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
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
