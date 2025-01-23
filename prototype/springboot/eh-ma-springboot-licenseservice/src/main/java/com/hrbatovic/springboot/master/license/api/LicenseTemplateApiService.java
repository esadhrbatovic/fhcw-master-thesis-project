package com.hrbatovic.springboot.master.license.api;

import com.hrbatovic.master.springboot.license.api.LicenseTemplatesApi;
import com.hrbatovic.master.springboot.license.model.*;
import com.hrbatovic.springboot.master.license.ApiInputValidator;
import com.hrbatovic.springboot.master.license.ClaimUtils;
import com.hrbatovic.springboot.master.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.springboot.master.license.db.repositories.LicenseTemplateRepository;
import com.hrbatovic.springboot.master.license.exceptions.EhMaException;
import com.hrbatovic.springboot.master.license.mapper.MapUtil;
import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateCreatedEvent;
import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateDeletedEvent;
import com.hrbatovic.springboot.master.license.messaging.model.out.LicenseTemplateUpdatedEvent;
import com.hrbatovic.springboot.master.license.messaging.producers.LicenseTemplateCreatedProducer;
import com.hrbatovic.springboot.master.license.messaging.producers.LicenseTemplateDeletedProducer;
import com.hrbatovic.springboot.master.license.messaging.producers.LicenseTemplateUpdatedProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class LicenseTemplateApiService implements LicenseTemplatesApi {

    @Autowired
    LicenseTemplateRepository licenseTemplateRepository;

    @Autowired
    LicenseTemplateCreatedProducer licenseTemplateCreatedProducer;

    @Autowired
    LicenseTemplateUpdatedProducer licenseTemplateUpdatedProducer;

    @Autowired
    LicenseTemplateDeletedProducer licenseTemplateDeletedProducer;

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        ApiInputValidator.validateCreateLicense(createLicenseTemplateRequest);
        LicenseTemplateEntity licenseTemplateEntity = mapper.toTemplateEntity(createLicenseTemplateRequest);
        licenseTemplateRepository.save(licenseTemplateEntity);
        licenseTemplateCreatedProducer.send(buildLicenseTemplateCreatedEvent(licenseTemplateEntity));

        return mapper.toApi(licenseTemplateEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);


        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        licenseTemplateDeletedProducer.send(buildLicenseTemplateDeletedEvent(licenseTemplateEntity));
        licenseTemplateRepository.delete(licenseTemplateEntity);

        return new DeleteLicenseTemplateResponse().message("License template deleted successfully.");
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);

        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        return mapper.toApi(licenseTemplateEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateListResponse listLicenseTemplates() {
        List<LicenseTemplateEntity> licenseTemplateEntities = licenseTemplateRepository.findAll();
        LicenseTemplateListResponse licenseTemplateListResponse = new LicenseTemplateListResponse();

        licenseTemplateListResponse.setLicenseTemplates(mapper.toApiList(licenseTemplateEntities));

        return licenseTemplateListResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        ApiInputValidator.validateProductId(productId);
        ApiInputValidator.validateUpdateLicense(updateLicenseTemplateRequest);

        LicenseTemplateEntity licenseTemplateEntity = licenseTemplateRepository.findByProductId(productId).orElse(null);

        if(licenseTemplateEntity == null){
            throw new EhMaException(404, "License template not found.");
        }

        mapper.patch(updateLicenseTemplateRequest, licenseTemplateEntity);
        licenseTemplateRepository.save(licenseTemplateEntity);

        licenseTemplateUpdatedProducer.send(buildLicenseTempalteUpdatedEvent(licenseTemplateEntity));
        return mapper.toApi(licenseTemplateEntity);
    }

    private LicenseTemplateUpdatedEvent buildLicenseTempalteUpdatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setLicenseTemplate(mapper.map(licenseTemplateEntity));
    }

    private LicenseTemplateCreatedEvent buildLicenseTemplateCreatedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateCreatedEvent()
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setTimestamp(LocalDateTime.now())
                .setLicenseTemplate(mapper.map(licenseTemplateEntity));
    }

    private LicenseTemplateDeletedEvent buildLicenseTemplateDeletedEvent(LicenseTemplateEntity licenseTemplateEntity) {
        return new LicenseTemplateDeletedEvent()
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setTimestamp(LocalDateTime.now())
                .setProductId(licenseTemplateEntity.getProductId());
    }
}
