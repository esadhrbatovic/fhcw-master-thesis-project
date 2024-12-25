package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.LicenseTemplatesApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.license.api.LicenseTemplatesLicenseRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class LicenseTemplatesApiService implements LicenseTemplatesApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    LicenseTemplatesLicenseRestClient licenseTemplatesLicenseRestClient;

    @Override
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesLicenseRestClient.createLicenseTemplate(mapper.map(createLicenseTemplateRequest)));
    }

    @Override
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        return mapper.map(licenseTemplatesLicenseRestClient.deleteLicenseTemplate(productId));
    }

    @Override
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        return mapper.map(licenseTemplatesLicenseRestClient.getLicenseTemplateByProductId(productId));
    }

    @Override
    public LicenseTemplateListResponse listLicenseTemplates() {
        return mapper.map(licenseTemplatesLicenseRestClient.listLicenseTemplates());
    }

    @Override
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesLicenseRestClient.updateLicenseTemplate(productId, mapper.map(updateLicenseTemplateRequest)));
    }
}
