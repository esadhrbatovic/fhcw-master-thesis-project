package com.hrbatovic.master.license.api;

import com.hrbatovic.master.quarkus.license.api.LicensesApi;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponse;
import com.hrbatovic.master.quarkus.license.model.LicenseResponse;
import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class LicenseApiService implements LicensesApi {

    @Override
    public LicenseResponse getLicenseBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        return null;
    }
}
