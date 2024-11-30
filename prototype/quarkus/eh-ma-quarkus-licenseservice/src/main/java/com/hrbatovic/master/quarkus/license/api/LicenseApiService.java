package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponse;
import com.hrbatovic.master.quarkus.license.model.LicenseResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class LicenseApiService implements LicensesApi {

    @Inject
    MapUtil mapper;

    @Override
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        LicenseEntity licenseEntity = LicenseEntity.findById(serialNumber);

        //TODO: error handling
        return mapper.toApi(licenseEntity);
    }

    @Override
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        //TODO: pagination and filtering and sorting
        //TODO: implement

        return null;
    }
}
