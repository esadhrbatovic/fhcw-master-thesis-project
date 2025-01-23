package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.CategoryManagementApi;
import com.hrbatovic.micronaut.master.apigateway.api.ProductManagementApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Singleton
public class ProductApiService implements ProductManagementApi, CategoryManagementApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.product.api.ProductManagementApi productManagementApiClient;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.product.api.CategoryManagementApi categoryManagementApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Category getCategoryById(UUID id, String authorization) {
        return mapper.map(categoryManagementApiClient.getCategoryById(id, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public CategoryListResponse listCategories(String authorization, Integer page, Integer limit, String search) {
        return mapper.map(categoryManagementApiClient.listCategories(authorization, page, limit, search));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public ProductResponse createProduct(String authorization, CreateProductRequest createProductRequest) {
        return mapper.map(productManagementApiClient.createProduct(authorization, mapper.map(createProductRequest)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public DeleteProductResponse deleteProduct(UUID id, String authorization) {
        return mapper.map(productManagementApiClient.deleteProduct(id, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public ProductResponse getProductById(UUID id, String authorization) {
        return mapper.map(productManagementApiClient.getProductById(id, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin", "customer"})
    public ProductListResponse listProducts(String authorization, Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, ListProductsSortParameter sort) {
        return mapper.map(productManagementApiClient.listProducts(authorization, page, limit, search, category, priceMin, priceMax, createdAfter, createdBefore, mapper.toListSort(sort)));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public ProductResponse updateProduct(UUID id, String authorization, UpdateProductRequest updateProductRequest) {
        return mapper.map(productManagementApiClient.updateProduct(id, authorization, mapper.map(updateProductRequest)));
    }
}
