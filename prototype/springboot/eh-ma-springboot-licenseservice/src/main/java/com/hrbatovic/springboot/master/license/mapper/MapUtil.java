package com.hrbatovic.springboot.master.license.mapper;

import com.hrbatovic.master.springboot.license.model.*;
import com.hrbatovic.springboot.master.license.db.entities.LicenseEntity;
import com.hrbatovic.springboot.master.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.springboot.master.license.db.entities.UserEntity;
import com.hrbatovic.springboot.master.license.messaging.model.in.payload.UserPayload;
import com.hrbatovic.springboot.master.license.messaging.model.out.payload.LicensePayload;
import com.hrbatovic.springboot.master.license.messaging.model.out.payload.LicenseTemplatePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MapUtil {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    public abstract LicenseTemplateEntity toTemplateEntity(CreateLicenseTemplateRequest createLicenseTemplateRequest);

    public abstract LicenseTemplateResponse toApi(LicenseTemplateEntity licenseTemplateEntity);

    public abstract List<LicenseTemplate> toApiList(List<LicenseTemplateEntity> licenseTemplateEntities);

    @Mapping(target="id", ignore = true)
    @Mapping(target="productId", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", ignore = true)
    public abstract void patch(UpdateLicenseTemplateRequest updateLicenseTemplateRequest, @MappingTarget LicenseTemplateEntity licenseTemplateEntity);

    public abstract LicenseResponse toApi(LicenseEntity licenseEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    public abstract List<LicensePayload> map(List<LicenseEntity> licenses);

    public abstract LicenseTemplatePayload map(LicenseTemplateEntity licenseTemplateEntity);

    public abstract List<License> toApiLicenseList(List<LicenseEntity> licenseEntities);
}
