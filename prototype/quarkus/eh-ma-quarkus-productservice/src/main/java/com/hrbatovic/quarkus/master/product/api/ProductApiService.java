package com.hrbatovic.quarkus.master.product.api;

import com.aayushatharva.brotli4j.common.annotations.Local;
import com.hrbatovic.master.quarkus.product.api.ProductsApi;
import com.hrbatovic.master.quarkus.product.model.*;
import com.hrbatovic.quarkus.master.product.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.product.db.entities.CategoryEntity;
import com.hrbatovic.quarkus.master.product.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.product.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.product.mapper.MapUtil;
import com.hrbatovic.quarkus.master.product.messaging.model.out.ProductCreatedEvent;
import com.hrbatovic.quarkus.master.product.messaging.model.out.ProductUpdatedEvent;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class ProductApiService implements ProductsApi {

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    @Claim(standard = Claims.email)
    String emailClaim;

    @Inject
    @Claim("sid")
    String sessionIdClaim;

    @Inject
    @Claim(standard = Claims.groups)
    Set<String> groupsClaim;

    @Inject
    MapUtil mapper;

    @Inject
    @Channel("product-created-out")
    Emitter<ProductCreatedEvent> productCreatedEmitter;

    @Inject
    @Channel("product-updated-out")
    Emitter<ProductUpdatedEvent> productUpdatedEmitter;

    @Inject
    @Channel("product-deleted-out")
    Emitter<UUID> productDeletedEmitter;

    @Override
    @RolesAllowed({"admin", "customer"})
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        PanacheQuery<ProductEntity> query = ProductEntity.queryProducts(page, limit, search, category, priceMin, priceMax, createdAfter, createdBefore, sort);

        List<ProductEntity> productEntityList = query.list();
        if(groupsClaim.contains("customer") && !groupsClaim.contains("admin")){
            productEntityList = productEntityList.stream().filter(p->!p.isDeleted()).toList();
        }

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

        ProductListResponse productListResponse = new ProductListResponse();
        if(groupsClaim.contains("admin") && !groupsClaim.contains("customer")){
            productListResponse.setProducts(mapper.mapAdminList(productEntityList, categoryMap));
        }else if(groupsClaim.contains("customer") && !groupsClaim.contains("admin")){
            productListResponse.setProducts(mapper.mapNonAdminList(productEntityList, categoryMap));
        }

        long totalItems = query.count();
        int totalPages = query.pageCount();

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);
        productListResponse.setPagination(pagination);

        return productListResponse;
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public ProductResponse getProductById(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        ProductEntity productEntity = ProductEntity.findById(productId);

        if (productEntity == null) {
            throw new EhMaException(404, "Product with ID " + productId + " not found");
        }

        CategoryEntity categoryEntity = CategoryEntity.findById(productEntity.getCategoryId());
        String categoryName = Optional.ofNullable(categoryEntity)
                .map(CategoryEntity::getName)
                .orElse("Unknown");
        ProductResponse productResponse = new ProductResponse();
        if(groupsClaim.contains("admin") && !groupsClaim.contains("customer")){
            productResponse = mapper.mapAdmin(productEntity, categoryName);
        }else if(groupsClaim.contains("customer") && !groupsClaim.contains("admin")){
            productResponse = mapper.mapNonAdmin(productEntity, categoryName);
        }

        return productResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        ApiInputValidator.validateProductId(productId);
        ApiInputValidator.validateUpdateProduct(updateProductRequest);

        ProductEntity productEntity = ProductEntity.findById(productId);
        if (productEntity == null) {
            throw new EhMaException(404, "Product with ID " + productId + " not found");
        }

        CategoryEntity categoryEntity = CategoryEntity.findById(productEntity.getCategoryId());

        if (StringUtils.isNotEmpty(updateProductRequest.getCategory()) && !StringUtils.equals(updateProductRequest.getCategory(), categoryEntity.getName())) {
            categoryEntity = CategoryEntity.find("name", updateProductRequest.getCategory()).firstResult();
            if (categoryEntity == null) {
                categoryEntity = new CategoryEntity();
                categoryEntity.setName(updateProductRequest.getCategory());
                categoryEntity.persist();
            }
        }

        mapper.update(productEntity, updateProductRequest, categoryEntity);

        productEntity.update();

        productUpdatedEmitter.send(buildProductUpdatedEvent(productEntity));
        return mapper.mapAdmin(productEntity, categoryEntity.getName());
    }


    @Override
    @RolesAllowed({"admin"})
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        ApiInputValidator.validateCreateProdocut(createProductRequest);
        CategoryEntity category = CategoryEntity.find("name", createProductRequest.getCategory()).firstResult();
        if (category == null) {
            category = new CategoryEntity();
            category.setName(createProductRequest.getCategory());
            category.persist();
        }

        ProductEntity productEntity = mapper.map(createProductRequest, category.getId());

        productEntity.persist();
        productCreatedEmitter.send(buildProductCreatedEvent(productEntity));
        return mapper.mapAdmin(productEntity, category.getName());
    }

    @Override
    @RolesAllowed({"admin"})
    public DeleteProductResponse deleteProduct(UUID productId) {
        ApiInputValidator.validateProductId(productId);
        ProductEntity productEntity = ProductEntity.findById(productId);

        if (productEntity == null) {
            throw new EhMaException(404, "Product with ID " + productId + " not found");
        }

        productEntity.setDeleted(true);
        productEntity.setUpdatedAt(LocalDateTime.now());
        productEntity.update();
        productDeletedEmitter.send(productId);
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product successfully deleted.");
        return response;
    }

    private ProductUpdatedEvent buildProductUpdatedEvent(ProductEntity productEntity) {
        return new ProductUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(UUID.fromString(userSubClaim))
                .setUserEmail(emailClaim)
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }

    private ProductCreatedEvent buildProductCreatedEvent(ProductEntity productEntity) {
        return new ProductCreatedEvent()
                .setUserId(UUID.fromString(userSubClaim))
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(emailClaim)
                .setSessionId(UUID.fromString(sessionIdClaim))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }

}
