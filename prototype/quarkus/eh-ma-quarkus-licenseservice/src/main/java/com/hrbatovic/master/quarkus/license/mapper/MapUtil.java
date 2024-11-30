package com.hrbatovic.master.quarkus.license.mapper;

import com.hrbatovic.master.quarkus.license.db.entities.LicenseTemplateEntity;
import com.hrbatovic.master.quarkus.license.model.CreateLicenseTemplateRequest;
import com.hrbatovic.master.quarkus.license.model.LicenseTemplate;
import com.hrbatovic.master.quarkus.license.model.LicenseTemplateResponse;
import com.hrbatovic.master.quarkus.license.model.UpdateLicenseTemplateRequest;
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
}

