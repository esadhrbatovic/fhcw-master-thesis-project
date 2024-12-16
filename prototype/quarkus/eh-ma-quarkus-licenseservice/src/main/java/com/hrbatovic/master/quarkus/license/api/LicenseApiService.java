package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponse;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponsePagination;
import com.hrbatovic.master.quarkus.license.model.LicenseResponse;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.*;

@RequestScoped
public class LicenseApiService implements LicensesApi {

    @Inject
    MapUtil mapper;

    @Override
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        LicenseEntity licenseEntity = LicenseEntity.findById(serialNumber);

        if (licenseEntity == null) {
            throw new RuntimeException("License not found for serial number: " + serialNumber);
        }

        return mapper.toApi(licenseEntity);
    }

    @Override
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        PanacheQuery<LicenseEntity> query = LicenseEntity.queryLicenses(userId, productId, sort, page, limit);

        List<LicenseEntity> licenseEntities = query.list();
        long totalItems = query.count();
        int totalPages = query.pageCount();

        LicenseListResponse response = new LicenseListResponse();
        response.setLicenses(mapper.toApiLicenseList(licenseEntities));
        response.setPagination(createPagination(page, limit, totalItems, totalPages));

        return response;
    }

    private LicenseListResponsePagination createPagination(Integer page, Integer limit, long totalItems, int totalPages) {
        LicenseListResponsePagination pagination = new LicenseListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);
        return pagination;
    }
}
