package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.LicenseTemplatesApi;
import com.hrbatovic.micronaut.master.apigateway.api.LicensesApi;
import com.hrbatovic.micronaut.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
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
        try {
            return mapper.map(licensesApiClient.getLicenseBySerialNumber(serialNumber, authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseListResponse listLicenses(String authorization, Integer page, Integer limit, UUID userId, UUID productId, ListLicensesSortParameter sort) {
        try {
            return mapper.map(licensesApiClient.listLicenses(authorization, page, limit, userId, productId, mapper.map(sort)));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse createLicenseTemplate(String authorization, CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        try {
            return mapper.map(licenseTemplatesApiClient.createLicenseTemplate(authorization, mapper.map(createLicenseTemplateRequest)));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId, String authorization) {
        try {
            return mapper.map(licenseTemplatesApiClient.deleteLicenseTemplate(productId, authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId, String authorization) {
        try {
            return mapper.map(licenseTemplatesApiClient.getLicenseTemplateByProductId(productId, authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateListResponse listLicenseTemplates(String authorization) {
        try {
            return mapper.map(licenseTemplatesApiClient.listLicenseTemplates(authorization));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, String authorization, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        try {
            return mapper.map(licenseTemplatesApiClient.updateLicenseTemplate(productId, authorization, mapper.map(updateLicenseTemplateRequest)));
        } catch (
                HttpClientResponseException e) {
            throw new EhMaException(e.getStatus().getCode(), e.getMessage());
        }
    }
}
