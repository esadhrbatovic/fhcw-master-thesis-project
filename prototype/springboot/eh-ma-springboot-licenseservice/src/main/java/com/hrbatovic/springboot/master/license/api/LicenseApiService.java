package com.hrbatovic.springboot.master.license.api;

import com.hrbatovic.master.springboot.license.api.LicensesApi;
import com.hrbatovic.master.springboot.license.model.LicenseListResponse;
import com.hrbatovic.master.springboot.license.model.LicenseListResponsePagination;
import com.hrbatovic.master.springboot.license.model.LicenseResponse;
import com.hrbatovic.springboot.master.license.ClaimUtils;
import com.hrbatovic.springboot.master.license.db.entities.LicenseEntity;
import com.hrbatovic.springboot.master.license.db.repositories.LicenseRepository;
import com.hrbatovic.springboot.master.license.exceptions.EhMaException;
import com.hrbatovic.springboot.master.license.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class LicenseApiService implements LicensesApi {

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        LicenseEntity licenseEntity = licenseRepository.findById(serialNumber).orElse(null);

        if (licenseEntity == null) {
            throw new EhMaException(404, "License not found for requested serial number");
        }

        if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){
            if (!licenseEntity.getUserId().equals(claimUtils.getUUIDClaim("sub"))) {
                /**
                 *  Here i say the license is not found.
                 *  I don't want to communicate to the user, that he can't view another user's license number, because that would
                 *  give him the information that the serial number he requested belongs to another user.
                 */
                throw new EhMaException(404, "License not found for requested serial number.");
            }
        }

        return mapper.toApi(licenseEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
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
