package com.hrbatovic.micronaut.master.license.api;

import com.hrbatovic.micronaut.master.license.JwtUtil;
import com.hrbatovic.micronaut.master.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.micronaut.master.license.db.repositories.LicenseTemplateRepository;
import com.hrbatovic.micronaut.master.license.mapper.MapUtil;
import com.hrbatovic.micronaut.master.license.messaging.model.out.LicenseTemplateCreatedEvent;
import com.hrbatovic.micronaut.master.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import com.hrbatovic.micronaut.master.license.messaging.producers.LicenseTemplateCreatedProducer;
import com.hrbatovic.micronaut.master.license.messaging.producers.LicenseTemplateDeletedProducer;
import com.hrbatovic.micronaut.master.license.messaging.producers.LicenseTemplateUpdatedProducer;
import com.hrbatovic.micronaut.master.license.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class LicenseTemplateApiService implements LicenseTemplatesApi {

    @Inject
    LicenseTemplateRepository licenseTemplateRepository;

    @Inject
    LicenseTemplateCreatedProducer licenseTemplateCreatedProducer;

    @Inject
    LicenseTemplateUpdatedProducer licenseTemplateUpdatedProducer;

    @Inject
    LicenseTemplateDeletedProducer licenseTemplateDeletedProducer;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        LicenseTemplateEntity licenseTemplateEntity = mapper.toTemplateEntity(createLicenseTemplateRequest);
        licenseTemplateRepository.save(licenseTemplateEntity);
        licenseTemplateCreatedProducer.send(buildLicenseTemplateCreatedEvent(licenseTemplateEntity));

        return mapper.toApi(licenseTemplateEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);


        if(licenseTemplateEntity == null){
            throw new RuntimeException("License template not found.");
        }

        licenseTemplateDeletedProducer.send(licenseTemplateEntity.getProductId());
        licenseTemplateRepository.delete(licenseTemplateEntity);

        return new DeleteLicenseTemplateResponse().message("License template deleted successfully.");
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);

        if(licenseTemplateEntity == null){
            throw new RuntimeException("License template not found.");
        }

        return mapper.toApi(licenseTemplateEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateListResponse listLicenseTemplates() {
        List<LicenseTemplateEntity> licenseTemplateEntities = licenseTemplateRepository.findAll();
        LicenseTemplateListResponse licenseTemplateListResponse = new LicenseTemplateListResponse();

        licenseTemplateListResponse.setLicenseTemplates(mapper.toApiList(licenseTemplateEntities));

        return licenseTemplateListResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);

        if(licenseTemplateEntity == null){
            throw new RuntimeException("License template not found.");
        }

        mapper.patch(updateLicenseTemplateRequest, licenseTemplateEntity);
        licenseTemplateRepository.update(licenseTemplateEntity);

        licenseTemplateUpdatedProducer.send(buildLicenseTempalteUpdatedEvent(licenseTemplateEntity));
        return null;
    }

    private LicenseTemplateUpdatedEvent buildLicenseTempalteUpdatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(jwtUtil.getClaimFromSecurityContext("email"))
                .setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))
                .setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")))
                .setLicenseTemplate(mapper.map(licenseTemplateEntity));
    }

    private LicenseTemplateCreatedEvent buildLicenseTemplateCreatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateCreatedEvent()
                .setUserEmail(jwtUtil.getClaimFromSecurityContext("email"))
                .setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))
                .setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")))
                .setTimestamp(LocalDateTime.now())
                .setLicenseTemplate(mapper.map(licenseTemplateEntity));
    }
}
