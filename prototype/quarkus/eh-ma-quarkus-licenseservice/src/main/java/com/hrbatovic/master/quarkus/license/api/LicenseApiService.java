package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.api.validators.ApiInputValidator;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.exceptions.EhMaException;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponse;
import com.hrbatovic.master.quarkus.license.model.LicenseListResponsePagination;
import com.hrbatovic.master.quarkus.license.model.LicenseResponse;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.*;

@RequestScoped
public class LicenseApiService implements LicensesApi {

    @Inject
    @Claim(standard = Claims.groups)
    List<String> groupsClaim;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    MapUtil mapper;

    @Override
    public Response getLicenseBySerialNumber(UUID serialNumber) {
        ApiInputValidator.validateSerialNumber(serialNumber);

        LicenseEntity licenseEntity = LicenseEntity.findById(serialNumber);

        if (licenseEntity == null) {
            throw new EhMaException(404, "License not found for requested serial number");
        }

        if (groupsClaim.contains("customer") && !groupsClaim.contains("admin")) {
            if (licenseEntity.getUserId() != UUID.fromString(userSubClaim)) {
                /**
                 *  Here i say the license is not found.
                 *  I don't want to communicate to the user, that he can't view another user's license number, because that would
                 *  give him the information that the serial number he requested belongs to another user.
                 */
                throw new EhMaException(404, "License not found for requested serial number.");
            }
        }


        return Response.ok(mapper.toApi(licenseEntity)).status(200).build();
    }

    @Override
    public Response listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        PanacheQuery<LicenseEntity> query = LicenseEntity.queryLicenses(userId, productId, sort, page, limit);

        List<LicenseEntity> licenseEntities = query.list();
        long totalItems = query.count();
        int totalPages = query.pageCount();

        LicenseListResponse response = new LicenseListResponse();
        response.setLicenses(mapper.toApiLicenseList(licenseEntities));
        response.setPagination(createPagination(page, limit, totalItems, totalPages));

        return Response.ok(response).status(200).build();
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
