package com.hrbatovic.master.product.api;

import com.hrbatovic.master.quarkus.product.api.ProductsApi;
import com.hrbatovic.master.quarkus.product.model.*;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class ProductApiService implements ProductsApi {

    @Override
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        return null;
    }

    @Override
    public ProductResponse getProductById(UUID productId) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        return null;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        return null;
    }

    @Override
    public DeleteProductResponse deleteProduct(UUID productId) {
        return null;
    }

}
