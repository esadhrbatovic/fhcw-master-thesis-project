package com.hrbatovic.micronaut.master.license.api;

import com.hrbatovic.micronaut.master.license.JwtUtil;
import com.hrbatovic.micronaut.master.license.db.entities.LicenseEntity;
import com.hrbatovic.micronaut.master.license.db.repositories.LicenseRepository;
import com.hrbatovic.micronaut.master.license.mapper.MapUtil;
import com.hrbatovic.micronaut.master.license.model.LicenseListResponse;
import com.hrbatovic.micronaut.master.license.model.LicenseListResponsePagination;
import com.hrbatovic.micronaut.master.license.model.LicenseResponse;
import com.hrbatovic.micronaut.master.license.model.ListLicensesSortParameter;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class LicenseApiService implements LicensesApi {

    @Inject
    LicenseRepository licenseRepository;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin", "customer"})
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        LicenseEntity licenseEntity = licenseRepository.findById(serialNumber).orElse(null);

        if (licenseEntity == null) {
            throw new RuntimeException("License not found for requested serial number");
        }

        if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            if (!licenseEntity.getUserId().equals(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))) {
                /**
                 *  Here i say the license is not found.
                 *  I don't want to communicate to the user, that he can't view another user's license number, because that would
                 *  give him the information that the serial number he requested belongs to another user.
                 */
                throw new RuntimeException("License not found for requested serial number.");
            }
        }

        return mapper.toApi(licenseEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, ListLicensesSortParameter sort) {
        String sortString = sort != null ? sort.toString() : null;

        List<LicenseEntity> licenseEntities = licenseRepository.queryLicenses(page, limit, userId, productId, sortString);

        LicenseListResponse response = new LicenseListResponse();
        response.setLicenses(mapper.toApiLicenseList(licenseEntities));

        LicenseListResponsePagination pagination = new LicenseListResponsePagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems(licenseEntities.size());
        pagination.setTotalPages((int) Math.ceil((double) licenseEntities.size() / (limit != null ? limit : 10)));

        response.setPagination(pagination);
        return response;
    }
}
