package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.LicenseTemplatesApi;
import com.hrbat.quarkus.master.apigateway.api.LicensesApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.license.api.LicenseTemplatesLicenseRestClient;
import com.hrbat.quarkus.master.apigateway.model.license.api.LicensesLicenseRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@RequestScoped
public class LicenseApiService implements LicensesApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    LicensesLicenseRestClient licensesLicenseRestClient;

    @Override
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        return mapper.map(licensesLicenseRestClient.getLicenseBySerialNumber(serialNumber));
    }

    @Override
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        return mapper.map(licensesLicenseRestClient.listLicenses(page, limit, userId, productId, sort));
    }


}
