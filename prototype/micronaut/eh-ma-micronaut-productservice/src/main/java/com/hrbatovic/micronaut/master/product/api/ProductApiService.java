package com.hrbatovic.micronaut.master.product.api;

import com.hrbatovic.micronaut.master.product.ApiInputValidator;
import com.hrbatovic.micronaut.master.product.JwtUtil;
import com.hrbatovic.micronaut.master.product.db.entities.CategoryEntity;
import com.hrbatovic.micronaut.master.product.db.entities.ProductEntity;
import com.hrbatovic.micronaut.master.product.db.repositories.CategoryRepository;
import com.hrbatovic.micronaut.master.product.db.repositories.ProductRepository;
import com.hrbatovic.micronaut.master.product.mapper.MapUtil;
import com.hrbatovic.micronaut.master.product.messaging.model.out.ProductCreatedEvent;
import com.hrbatovic.micronaut.master.product.messaging.model.out.ProductUpdatedEvent;
import com.hrbatovic.micronaut.master.product.messaging.producers.ProductCreatedEventProducer;
import com.hrbatovic.micronaut.master.product.messaging.producers.ProductDeletedEventProducer;
import com.hrbatovic.micronaut.master.product.messaging.producers.ProductUpdatedEventProducer;
import com.hrbatovic.micronaut.master.product.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Controller
public class ProductApiService implements ProductManagementApi{

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductCreatedEventProducer productCreatedEventProducer;

    @Inject
    ProductUpdatedEventProducer productUpdatedEventProducer;

    @Inject
    ProductDeletedEventProducer productDeletedEventProducer;

    @Inject
    MapUtil mapper;

    @Inject
    JwtUtil jwtUtil;

    @Override
    @RolesAllowed({"admin", "customer"})
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, ListProductsSortParameter sort) {

        String sortString = sort != null ? sort.toString() : null;
        UUID categoryId = null;

        if (category != null && !category.isEmpty()) {
            CategoryEntity categoryEntity = categoryRepository.findByNameRegex("^" + category + ".*$").orElse(null);
            if (categoryEntity != null) {
                categoryId = categoryEntity.getId();
            }
        }

        List<ProductEntity> productEntityList = productRepository.queryProducts(page, limit, search, categoryId, priceMin, priceMax, createdAfter, createdBefore, sortString);
        if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            productEntityList = productEntityList.stream().filter(p->!p.isDeleted()).toList();
        }

        List<UUID> categoryIds = productEntityList.stream()
                .map(ProductEntity::getCategoryId)
                .distinct()
                .toList();

        Map<UUID, String> categoryMap = categoryRepository.findByIds(categoryIds).stream()
                .map(CategoryEntity.class::cast)
                .collect(Collectors.toMap(
                        c -> UUID.fromString(c.getId().toString()),
                        CategoryEntity::getName
                ));

        ProductListResponse productListResponse = new ProductListResponse();

        if(jwtUtil.getRoles().contains("admin") && !jwtUtil.getRoles().contains("customer")){
            productListResponse.setProducts(mapper.mapAdminList(productEntityList, categoryMap));
        }else if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            productListResponse.setProducts(mapper.mapNonAdminList(productEntityList, categoryMap));
        }

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems(productEntityList.size());
        pagination.setTotalPages((int) Math.ceil((double) productEntityList.size() / (limit != null ? limit : 10)));
        productListResponse.setPagination(pagination);

        return productListResponse;
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public ProductResponse getProductById(UUID id) {
        ApiInputValidator.validateProductId(id);
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new RuntimeException("Product with ID " + id + " not found");
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productEntity.getCategoryId()).orElse(null);

        String categoryName = Optional.ofNullable(categoryEntity).map(CategoryEntity::getName).orElse("Unknown");
        ProductResponse productResponse = null;

        if(jwtUtil.getRoles().contains("admin") && !jwtUtil.getRoles().contains("customer")){
            productResponse = mapper.mapAdmin(productEntity, categoryName);
        }else if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){
            productResponse = mapper.mapNonAdmin(productEntity, categoryName);
        }

        return productResponse;
    }

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse updateProduct(UUID id, UpdateProductRequest updateProductRequest) {
        ApiInputValidator.validateProductId(id);
        ApiInputValidator.validateUpdateProduct(updateProductRequest);

        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new RuntimeException("Product with ID " + id + " not found");
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productEntity.getCategoryId()).orElse(null);

        if (StringUtils.isNotEmpty(updateProductRequest.getCategory()) && !StringUtils.equals(updateProductRequest.getCategory(), categoryEntity.getName())) {
            categoryEntity = categoryRepository.findByName(updateProductRequest.getCategory()).orElse(null);
            if(categoryEntity == null){
                categoryEntity = new CategoryEntity();
                categoryEntity.setName(updateProductRequest.getCategory());
                categoryRepository.save(categoryEntity);
            }
        }

        mapper.update(productEntity, updateProductRequest, categoryEntity);

        productRepository.update(productEntity);

        productUpdatedEventProducer.send(buildProductUpdatedEvent(productEntity));
        return mapper.mapAdmin(productEntity, categoryEntity.getName());
    }

    @Override
    @RolesAllowed({"admin"})
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        ApiInputValidator.validateCreateProdocut(createProductRequest);

        CategoryEntity category = categoryRepository.findByName(createProductRequest.getCategory()).orElse(null);
        if(category == null){
            category = new CategoryEntity();
            category.setName(createProductRequest.getCategory());
            categoryRepository.save(category);
        }

        ProductEntity productEntity = mapper.map(createProductRequest, category.getId());

        productRepository.save(productEntity);
        productCreatedEventProducer.send(buildProductCreatedEvent(productEntity));
        return mapper.mapAdmin(productEntity, category.getName());
    }

    @Override
    @RolesAllowed({"admin"})
    public DeleteProductResponse deleteProduct(UUID id) {
        ApiInputValidator.validateProductId(id);
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new RuntimeException("Product with ID " + id + " not found");
        }

        productEntity.setDeleted(true);
        productEntity.setUpdatedAt(LocalDateTime.now());
        productRepository.update(productEntity);

        productDeletedEventProducer.send(id);
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product successfully deleted.");
        return response;
    }


    private ProductUpdatedEvent buildProductUpdatedEvent(ProductEntity productEntity) {
        return new ProductUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))
                .setUserEmail(jwtUtil.getClaimFromSecurityContext("email"))
                .setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }

    private ProductCreatedEvent buildProductCreatedEvent(ProductEntity productEntity) {
        return new ProductCreatedEvent()
                .setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(jwtUtil.getClaimFromSecurityContext("email"))
                .setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }
}
