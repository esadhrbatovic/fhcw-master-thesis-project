package com.hrbatovic.springboot.master.product.messaging.model.in;

import com.hrbatovic.springboot.master.product.messaging.model.in.payload.LicenseTemplatePayload;

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

}
