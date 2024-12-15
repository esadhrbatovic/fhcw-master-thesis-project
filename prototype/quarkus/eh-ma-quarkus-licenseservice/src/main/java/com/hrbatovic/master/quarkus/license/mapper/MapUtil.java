package com.hrbatovic.master.quarkus.license.mapper;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseEntity;
import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.db.entities.UserEntity;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.in.UserUpdatedEvent;
import com.hrbatovic.master.quarkus.license.messaging.model.out.payload.LicensePayload;
import com.hrbatovic.master.quarkus.license.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {


    @Mapping(target = "id", ignore = true)
    public abstract LicenseTemplateEntity toTemplateEntity(CreateLicenseTemplateRequest createLicenseTemplateRequest);

    public abstract LicenseTemplateResponse toApi(LicenseTemplateEntity licenseTemplateEntity);

    public abstract List<LicenseTemplate> toApiList(List<LicenseTemplateEntity> licenseTemplateEntities);

    @Mapping(target="id", ignore = true)
    public abstract void patch(UpdateLicenseTemplateRequest updateLicenseTemplateRequest, @MappingTarget LicenseTemplateEntity licenseTemplateEntity);

    public abstract LicenseResponse toApi(LicenseEntity licenseEntity);

    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);

    public abstract void update(@MappingTarget UserEntity userEntity, UserUpdatedEvent userUpdatedEvent);

    public abstract List<LicensePayload> map(List<LicenseEntity> licenses);
}

