package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.ProductsApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.*;
import com.hrbat.quarkus.master.apigateway.model.product.api.ProductManagementProductRestClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class ProductApiService implements ProductsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    ProductManagementProductRestClient productManagementProductRestClient;

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        return mapper.map(productManagementProductRestClient.createProduct(mapper.map(createProductRequest)));
    }

    @Override
    @RolesAllowed({"admin"})
    public DeleteProductResponse deleteProduct(UUID id) {
        return mapper.map(productManagementProductRestClient.deleteProduct(id));
    }

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse getProductById(UUID id) {
        return mapper.map(productManagementProductRestClient.getProductById(id));
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        return mapper.map(productManagementProductRestClient.listProducts(page, limit, search, category, priceMin, priceMax, createdAfter, createdBefore, sort));
    }

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse updateProduct(UUID id, UpdateProductRequest updateProductRequest) {
        return mapper.map(productManagementProductRestClient.updateProduct(id, mapper.map(updateProductRequest)));
    }
}
