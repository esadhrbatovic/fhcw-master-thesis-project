package com.hrbatovic.springboot.master.product.api;

import com.hrbatovic.master.springboot.product.api.ProductsApi;
import com.hrbatovic.master.springboot.product.model.*;
import com.hrbatovic.springboot.master.product.ClaimUtils;
import com.hrbatovic.springboot.master.product.db.entities.CategoryEntity;
import com.hrbatovic.springboot.master.product.db.entities.ProductEntity;
import com.hrbatovic.springboot.master.product.db.repositories.CategoryRepository;
import com.hrbatovic.springboot.master.product.db.repositories.ProductRepository;
import com.hrbatovic.springboot.master.product.exceptions.EhMaException;
import com.hrbatovic.springboot.master.product.mapper.MapUtil;
import com.hrbatovic.springboot.master.product.messaging.model.out.ProductCreatedEvent;
import com.hrbatovic.springboot.master.product.messaging.model.out.ProductDeletedEvent;
import com.hrbatovic.springboot.master.product.messaging.model.out.ProductUpdatedEvent;
import com.hrbatovic.springboot.master.product.messaging.producers.ProductCreatedEventProducer;
import com.hrbatovic.springboot.master.product.messaging.producers.ProductDeletedEventProducer;
import com.hrbatovic.springboot.master.product.messaging.producers.ProductUpdatedEventProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProductApiService implements ProductsApi {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductCreatedEventProducer productCreatedEventProducer;

    @Autowired
    ProductUpdatedEventProducer productUpdatedEventProducer;

    @Autowired
    ProductDeletedEventProducer productDeletedEventProducer;

    @Autowired
    MapUtil mapper;

    @Autowired
    ClaimUtils claimUtils;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ProductListResponse listProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

        String sortString = sort != null ? sort.toString() : null;
        UUID categoryId = null;

        if (category != null && !category.isEmpty()) {
            CategoryEntity categoryEntity = categoryRepository.findByNameRegex("^" + category + ".*$").orElse(null);
            if (categoryEntity != null) {
                categoryId = categoryEntity.getId();
            }
        }

        List<ProductEntity> productEntityList = productRepository.queryProducts(page, limit, search, categoryId, priceMin, priceMax, createdAfter, createdBefore, sortString);
        if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){
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

        if(claimUtils.getRoles().contains("ROLE_ADMIN") && !claimUtils.getRoles().contains("ROLE_CUSTOMER")){
            productListResponse.setProducts(mapper.mapAdminList(productEntityList, categoryMap));
        }else if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){
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
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ProductResponse getProductById(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new EhMaException(404, "Product with ID " + id + " not found");
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productEntity.getCategoryId()).orElse(null);

        String categoryName = Optional.ofNullable(categoryEntity).map(CategoryEntity::getName).orElse("Unknown");
        ProductResponse productResponse = null;

        if(claimUtils.getRoles().contains("ROLE_ADMIN") && !claimUtils.getRoles().contains("ROLE_CUSTOMER")){
            productResponse = mapper.mapAdmin(productEntity, categoryName);
        }else if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){
            productResponse = mapper.mapNonAdmin(productEntity, categoryName);
        }

        return productResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ProductResponse updateProduct(UUID id, UpdateProductRequest updateProductRequest) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new EhMaException(404, "Product with ID " + id + " not found");
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

        productRepository.save(productEntity);

        productUpdatedEventProducer.send(buildProductUpdatedEvent(productEntity));
        return mapper.mapAdmin(productEntity, categoryEntity.getName());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DeleteProductResponse deleteProduct(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);

        if(productEntity == null){
            throw new EhMaException(404, "Product with ID " + id + " not found");
        }

        productEntity.setDeleted(true);
        productEntity.setUpdatedAt(LocalDateTime.now());
        productRepository.save(productEntity);

        productDeletedEventProducer.send(buildProductDeletedEvent(productEntity));
        DeleteProductResponse response = new DeleteProductResponse();
        response.setMessage("Product successfully deleted.");
        return response;
    }

    private ProductUpdatedEvent buildProductUpdatedEvent(ProductEntity productEntity) {
        return new ProductUpdatedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }

    private ProductDeletedEvent buildProductDeletedEvent(ProductEntity productEntity) {
        return new ProductDeletedEvent()
                .setTimestamp(LocalDateTime.now())
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setId(productEntity.getId())
                .setRequestCorrelationId(UUID.randomUUID());
    }

    private ProductCreatedEvent buildProductCreatedEvent(ProductEntity productEntity) {
        return new ProductCreatedEvent()
                .setUserId(claimUtils.getUUIDClaim("sub"))
                .setTimestamp(LocalDateTime.now())
                .setUserEmail(claimUtils.getStringClaim("email"))
                .setSessionId(claimUtils.getUUIDClaim("sid"))
                .setProduct(mapper.map(productEntity))
                .setRequestCorrelationId(UUID.randomUUID());
    }

}
