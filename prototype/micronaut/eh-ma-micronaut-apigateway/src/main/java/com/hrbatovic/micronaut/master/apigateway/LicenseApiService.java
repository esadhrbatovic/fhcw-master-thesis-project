package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.LicenseTemplatesApi;
import com.hrbatovic.micronaut.master.apigateway.api.LicensesApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.UUID;

@Controller
@Singleton
public class LicenseApiService implements LicensesApi, LicenseTemplatesApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.license.api.LicensesApi licensesApiClient;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber, String authorization) {
        return mapper.map(licensesApiClient.getLicenseBySerialNumber(serialNumber, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseListResponse listLicenses(String authorization, Integer page, Integer limit, UUID userId, UUID productId, ListLicensesSortParameter sort) {
        return mapper.map(licensesApiClient.listLicenses(authorization, page, limit, userId, productId, mapper.map(sort)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse createLicenseTemplate(String authorization, CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesApiClient.createLicenseTemplate(authorization, mapper.map(createLicenseTemplateRequest)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId, String authorization) {
        return mapper.map(licenseTemplatesApiClient.deleteLicenseTemplate(productId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId, String authorization) {
        return mapper.map(licenseTemplatesApiClient.getLicenseTemplateByProductId(productId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateListResponse listLicenseTemplates(String authorization) {
        return mapper.map(licenseTemplatesApiClient.listLicenseTemplates(authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, String authorization, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        return mapper.map(licenseTemplatesApiClient.updateLicenseTemplate(productId, authorization, mapper.map(updateLicenseTemplateRequest)));
    }
}
