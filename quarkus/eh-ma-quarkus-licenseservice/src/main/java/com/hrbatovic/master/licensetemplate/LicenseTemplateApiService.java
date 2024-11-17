package com.hrbatovic.master.licensetemplate;

import com.hrbatovic.master.quarkus.license.api.LicenseTemplatesApi;
import com.hrbatovic.master.quarkus.license.model.LicenseTemplateListResponse;
import com.hrbatovic.master.quarkus.license.model.LicenseTemplateResponse;
import com.hrbatovic.master.quarkus.license.model.UpsertLicenseTemplateRequest;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class LicenseTemplateApiService implements LicenseTemplatesApi {

    @Override
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        return null;
    }

    @Override
    public LicenseTemplateListResponse listLicenseTemplates() {
        return null;
    }

    @Override
    public LicenseTemplateResponse upsertLicenseTemplate(UUID productId, UpsertLicenseTemplateRequest upsertLicenseTemplateRequest) {
        return null;
    }
}
