package com.hrbatovic.master.quarkus.license.api;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.mapper.MapUtil;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicenseTemplateCreatedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import com.hrbatovic.master.quarkus.license.model.*;
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
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        LicenseTemplateEntity licenseTemplateEntity = mapping.toTemplateEntity(createLicenseTemplateRequest);
        licenseTemplateEntity.persist();

        licenseTemplateCreatedEmitter.send(buildLicenseTemplateCreatedEvent(licenseTemplateEntity));

        return mapping.toApi(licenseTemplateEntity);
    }


    @Override
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        LicenseTemplateEntity licenseTemplateEntity = LicenseTemplateEntity.find("productId", productId).firstResult();

        licenseTemplateDeletedEmitter.send(licenseTemplateEntity.getProductId());
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
                .setUserEmail(userSubClaim)
                .setUserId(UUID.fromString(userSubClaim))
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setTimestamp(LocalDateTime.now())
                .setLicenseTemplate(mapping.map(licenseTemplateEntity));
    }

}
