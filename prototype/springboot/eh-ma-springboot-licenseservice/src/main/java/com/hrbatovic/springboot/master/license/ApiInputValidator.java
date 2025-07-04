package com.hrbatovic.springboot.master.license;

import com.hrbatovic.master.springboot.license.model.CreateLicenseTemplateRequest;
import com.hrbatovic.master.springboot.license.model.UpdateLicenseTemplateRequest;
import com.hrbatovic.springboot.master.license.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {


    public static void validateSerialNumber(UUID serialNumber) {
        if (serialNumber == null || StringUtils.isEmpty(serialNumber.toString())) {
            throw new EhMaException(400, "Serial number must not be null.");
        }
    }

    public static void validateCreateLicense(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        if (createLicenseTemplateRequest == null || createLicenseTemplateRequest.getLicenseDuration() == null ||
                createLicenseTemplateRequest.getProductId() == null || StringUtils.isEmpty(createLicenseTemplateRequest.getProductId().toString())) {
            throw new EhMaException(400, "Not all license information provided");
        }
    }

    public static void validateProductId(UUID productId) {
        if (productId == null || StringUtils.isEmpty(productId.toString())) {
            throw new EhMaException(400, "Product id must not be empty");
        }
    }

    public static void validateUpdateLicense(UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        if (updateLicenseTemplateRequest == null || updateLicenseTemplateRequest.getLicenseDuration() == null || updateLicenseTemplateRequest.getLicenseDuration() < 1) {
            throw new EhMaException(400, "Not all update information provided.");
        }
    }
}
