package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.api.validators.ApiInputValidator;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.exceptions.EhMaException;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicenseTemplateCreatedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import com.hrbatovic.master.quarkus.license.model.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class LicenseTemplateApiService implements LicenseTemplatesApi {

    @Inject
    MapUtil mapping;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    @Claim(standard = Claims.email)
    String emailClaim;

    @Inject
    @Claim("sid")
    String sessionIdClaim;

    @Inject
    @Channel("license-template-created-out")
    Emitter<LicenseTemplateCreatedEvent> licenseTemplateCreatedEmitter;

    @Inject
    @Channel("license-template-deleted-out")
    Emitter<UUID> licenseTemplateDeletedEmitter;

    @Inject
    @Channel("license-template-updated-out")
    Emitter<LicenseTemplateUpdatedEvent> licenseTemplateUpdatedEmitter;

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        ApiInputValidator.validateCreateLicense(createLicenseTemplateRequest);

        LicenseTemplateEntity licenseTemplateEntity = mapping.toTemplateEntity(createLicenseTemplateRequest);
        licenseTemplateEntity.persist();

        licenseTemplateCreatedEmitter.send(buildLicenseTemplateCreatedEvent(licenseTemplateEntity));

        return mapping.toApi(licenseTemplateEntity);
    }


    @Override
    @RolesAllowed({"admin"})
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        ApiInputValidator.validateProductId(productId);

        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();

        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        licenseTemplateDeletedEmitter.send(licenseTemplateEntity.getProductId());

        licenseTemplateEntity.delete();

        return new DeleteLicenseTemplateResponse().message("License template deleted successfully.");
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        ApiInputValidator.validateProductId(productId);

        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();
        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        return mapping.toApi(licenseTemplateEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateListResponse listLicenseTemplates() {
        List<LicenseTemplateEntity> licenseTemplateEntities = LicenseTemplateEntity.listAll();

        LicenseTemplateListResponse licenseTemplateListResponse = new LicenseTemplateListResponse();

        licenseTemplateListResponse.setLicenseTemplates(mapping.toApiList(licenseTemplateEntities));

        return licenseTemplateListResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        ApiInputValidator.validateProductId(productId);
        ApiInputValidator.validateUpdateLicense(updateLicenseTemplateRequest);

        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();
        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        mapping.patch(updateLicenseTemplateRequest, licenseTemplateEntity);
        licenseTemplateEntity.update();

        licenseTemplateUpdatedEmitter.send(buildLicenseTempalteUpdatedEvent(licenseTemplateEntity));

        return mapping.toApi(licenseTemplateEntity);
    }

    private LicenseTemplateUpdatedEvent buildLicenseTempalteUpdatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(emailClaim)
                .setUserId(UUID.fromString(userSubClaim))
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setLicenseTemplate(mapping.map(licenseTemplateEntity));
    }

    private LicenseTemplateCreatedEvent buildLicenseTemplateCreatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateCreatedEvent()
                .setUserEmail(emailClaim)
                .setUserId(UUID.fromString(userSubClaim))
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setTimestamp(LocalDateTime.now())
                .setLicenseTemplate(mapping.map(licenseTemplateEntity));
    }

}
