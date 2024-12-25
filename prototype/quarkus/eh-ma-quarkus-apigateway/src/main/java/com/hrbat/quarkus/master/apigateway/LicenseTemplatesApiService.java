package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.LicenseTemplatesApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.license.api.LicenseTemplatesLicenseRestClient;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesLicenseRestClient.createLicenseTemplate(mapper.map(createLicenseTemplateRequest)));
    }

    @Override
    @RolesAllowed({"admin"})
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        return mapper.map(licenseTemplatesLicenseRestClient.deleteLicenseTemplate(productId));
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        return mapper.map(licenseTemplatesLicenseRestClient.getLicenseTemplateByProductId(productId));
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateListResponse listLicenseTemplates() {
        return mapper.map(licenseTemplatesLicenseRestClient.listLicenseTemplates());
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesLicenseRestClient.updateLicenseTemplate(productId, mapper.map(updateLicenseTemplateRequest)));
    }
}
