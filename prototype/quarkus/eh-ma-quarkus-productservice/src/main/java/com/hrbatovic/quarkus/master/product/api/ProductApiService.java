package com.hrbatovic.quarkus.master.product.api;

import com.hrbatovic.master.quarkus.product.api.ProductsApi;
import com.hrbatovic.master.quarkus.product.model.*;
import com.hrbatovic.quarkus.master.product.db.entities.CategoryEntity;
import com.hrbatovic.quarkus.master.product.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.product.mapper.Mapper;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO create endpoints for categories, rework product and categories relationship, optimize join

@RequestScoped
public class ProductApiService implements ProductsApi {

    @Inject
    @Channel("product-created-out")
    Emitter<ProductEntity> productCreatedEmitter;

    @Inject
    @Channel("product-updated-out")
    Emitter<ProductEntity> productUpdatedEmitter;

    @Inject
    @Channel("product-deleted-out")
    Emitter<UUID> productDeletedEmitter;

    @Override
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        PanacheQuery<ProductEntity> query = ProductEntity.findProducts(page, limit, search, category, priceMin, priceMax, createdAfter, createdBefore, sort);

        List<ProductEntity> productEntityList = query.list();

        //TODO: optimize
        List<UUID> categoryIds = productEntityList.stream()
                .map(ProductEntity::getCategoryId)
                .distinct()
                .collect(Collectors.toList());

        Map<UUID, String> categoryMap = CategoryEntity.find("_id in ?1", categoryIds).stream()
                .map(CategoryEntity.class::cast)
                .collect(Collectors.toMap(
                        c -> UUID.fromString(c.getId().toString()),
                        CategoryEntity::getName
                ));

        ProductListResponse response = new ProductListResponse();
        response.setProducts(Mapper.mapEntitiesToModels(productEntityList, categoryMap));

        long totalItems = query.count();
        int totalPages = query.pageCount();

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);
        response.setPagination(pagination);

        return response;
    }

    @Override
    public ProductResponse getProductById(UUID productId) {
        ProductEntity productEntity = ProductEntity.findById(productId);

        if (productEntity == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        CategoryEntity categoryEntity = CategoryEntity.findById(productEntity.getCategoryId());
        String categoryName = Optional.ofNullable(categoryEntity)
                .map(CategoryEntity::getName)
                .orElse("Unknown");

        return Mapper.mapEntityToResponse(productEntity, categoryName);
    }
    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        ProductEntity productEntity = ProductEntity.findById(productId);
        if (productEntity == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        CategoryEntity categoryEntity = CategoryEntity.findById(productEntity.getCategoryId());
        String categoryName = Optional.ofNullable(categoryEntity)
                .map(CategoryEntity::getName)
                .orElse(null);

        if(StringUtils.isNotEmpty(updateProductRequest.getCategory()) && !StringUtils.equals(updateProductRequest.getCategory(), categoryName)){
            CategoryEntity category = CategoryEntity.find("name", updateProductRequest.getCategory()).firstResult();
            if (category == null) {
                category = new CategoryEntity();
                category.setName(updateProductRequest.getCategory());
                category.persist();
            }
            productEntity.setCategoryId(category.getId());
            categoryName = category.getName();
        }

        productEntity.setTitle(updateProductRequest.getTitle());
        productEntity.setDescription(updateProductRequest.getDescription());
        productEntity.setPrice(BigDecimal.valueOf(updateProductRequest.getPrice()));
        productEntity.setImageUrl(updateProductRequest.getImageUrl());
        if(updateProductRequest.getTags() != null){
            productEntity.setTags(updateProductRequest.getTags());
        }
        productEntity.setDeleted(updateProductRequest.getDeleted());
        productEntity.setUpdatedAt(LocalDateTime.now());

        productEntity.persistOrUpdate();
        productUpdatedEmitter.send(productEntity);
        return Mapper.mapEntityToResponse(productEntity, categoryName);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        CategoryEntity category = CategoryEntity.find("name", createProductRequest.getCategory()).firstResult();
        if (category == null) {
            category = new CategoryEntity();
            category.setName(createProductRequest.getCategory());
            category.persist();
        }

        ProductEntity productEntity = Mapper.toEntity(createProductRequest, category.getId());

        productEntity.persist();
        productCreatedEmitter.send(productEntity);
        return Mapper.toResponse(productEntity, category.getName());
    }

    @Override
    public DeleteProductResponse deleteProduct(UUID productId) {
        ProductEntity productEntity = ProductEntity.findById(productId);

        if (productEntity == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        productEntity.delete();
        productDeletedEmitter.send(productId);
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product successfully deleted.");
        return response;
    }

}
