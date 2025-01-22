package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.gateway.api.LicenseTemplatesApi;
import com.hrbatovic.master.springboot.gateway.api.LicensesApi;
import com.hrbatovic.master.springboot.gateway.model.*;
import com.hrbatovic.springboot.master.apigateway.JwtAuthentication;
import com.hrbatovic.springboot.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.springboot.master.apigateway.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.hrbatovic.springboot.master.apigateway.JsonErrorParser.parseErrorMessage;

@RestController
public class LicenseApiService implements LicensesApi, LicenseTemplatesApi {

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse createLicenseTemplate(CreateLicenseTemplateRequest createLicenseTemplateRequest) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licenseTemplatesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseTemplateResponse response = mapper.map(licenseTemplatesApiClient.createLicenseTemplate(mapper.map(createLicenseTemplateRequest)));
                return response;
            }
            return null;
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DeleteLicenseTemplateResponse deleteLicenseTemplate(UUID productId) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licenseTemplatesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                DeleteLicenseTemplateResponse response = mapper.map(licenseTemplatesApiClient.deleteLicenseTemplate(productId));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse getLicenseTemplateByProductId(UUID productId) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licenseTemplatesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseTemplateResponse response = mapper.map(licenseTemplatesApiClient.getLicenseTemplateByProductId(productId));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateListResponse listLicenseTemplates() {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licenseTemplatesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseTemplateListResponse response = mapper.map(licenseTemplatesApiClient.listLicenseTemplates());
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseTemplateResponse updateLicenseTemplate(UUID productId, UpdateLicenseTemplateRequest updateLicenseTemplateRequest) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi licenseTemplatesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicenseTemplatesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licenseTemplatesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseTemplateResponse response = mapper.map(licenseTemplatesApiClient.updateLicenseTemplate(productId, mapper.map(updateLicenseTemplateRequest)));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public LicenseResponse getLicenseBySerialNumber(UUID serialNumber) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicensesApi licensesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicensesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licensesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseResponse response = mapper.map(licensesApiClient.getLicenseBySerialNumber(serialNumber));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public LicenseListResponse listLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.license.api.LicensesApi licensesApiClient = new com.hrbatovic.master.springboot.apigateway.clients.license.api.LicensesApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                licensesApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                LicenseListResponse response = mapper.map(licensesApiClient.listLicenses(page, limit, userId, productId, sort));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
