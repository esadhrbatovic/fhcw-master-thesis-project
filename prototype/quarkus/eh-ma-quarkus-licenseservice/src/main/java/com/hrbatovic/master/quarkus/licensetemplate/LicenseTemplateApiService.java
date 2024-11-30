package com.hrbatovic.master.quarkus.licensetemplate;

import com.hrbatovic.master.quarkus.license.api.LicenseTemplatesApi;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class LicenseTemplateApiService implements LicenseTemplatesApi {

    @Inject
    MapUtil mapping;

    @Override
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        LicenseTemplateEntity licenseTemplateEntity = mapping.toTemplateEntity(createLicenseTemplateRequest);
        licenseTemplateEntity.setCreatedAt(LocalDateTime.now());
        licenseTemplateEntity.persist();

        return mapping.toApi(licenseTemplateEntity);
    }

    @Override
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();
        //TODO: error handling
        licenseTemplateEntity.delete();


        return new DeleteLicenseTemplateResponse().message("License template deleted successfully.");
    }

    @Override
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();
        //TODO: error handling

        return mapping.toApi(licenseTemplateEntity);
    }

    @Override
    public LicenseTemplateListResponse listLicenseTemplates() {
        List<LicenseTemplateEntity> licenseTemplateEntities = LicenseTemplateEntity.listAll();

        LicenseTemplateListResponse licenseTemplateListResponse = new LicenseTemplateListResponse();

        licenseTemplateListResponse.setLicenseTemplates(mapping.toApiList(licenseTemplateEntities));

        return licenseTemplateListResponse;
    }

    @Override
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();
        //TODO: error handling

        mapping.patch(updateLicenseTemplateRequest, licenseTemplateEntity);
        licenseTemplateEntity.setUpdatedAt(LocalDateTime.now());
        licenseTemplateEntity.persistOrUpdate();

        return mapping.toApi(licenseTemplateEntity);
    }

}
