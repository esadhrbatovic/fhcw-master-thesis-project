package com.hrbatovic.quarkus.master.product.mapper;

import com.hrbatovic.master.quarkus.product.model.CreateProductRequest;
import com.hrbatovic.master.quarkus.product.model.Product;
import com.hrbatovic.master.quarkus.product.model.ProductResponse;
import com.hrbatovic.quarkus.master.product.db.entities.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Mapper {

    public static ProductEntity toEntity(CreateProductRequest request, UUID categoryId) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setTitle(request.getTitle());
        productEntity.setDescription(request.getDescription());
        productEntity.setCategoryId(categoryId);
        productEntity.setPrice(BigDecimal.valueOf(request.getPrice()));
        productEntity.setCurrency("EUR");
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        return productEntity;
    }

    public static ProductResponse toResponse(ProductEntity entity, String categoryName) {
        ProductResponse response = new ProductResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setCategory(categoryName);
        response.setPrice(entity.getPrice().floatValue());
        response.setCurrency(ProductResponse.CurrencyEnum.EUR);
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    public static Product mapEntityToModel(ProductEntity entity, String categoryName) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setTitle(entity.getTitle());
        product.setDescription(entity.getDescription());
        product.setCategory(categoryName);
        product.setPrice(entity.getPrice().floatValue());
        product.setCurrency(Product.CurrencyEnum.valueOf(entity.getCurrency()));
        product.setCreatedAt(entity.getCreatedAt());
        product.setUpdatedAt(entity.getUpdatedAt());
        return product;
    }

    public static List<Product> mapEntitiesToModels(List<ProductEntity> entities, Map<UUID, String> categoryMap) {
        return entities.stream()
                .map(entity -> mapEntityToModel(entity, categoryMap.get(entity.getCategoryId())))
                .collect(Collectors.toList());
    }

    public static ProductResponse mapEntityToResponse(ProductEntity entity, String categoryName) {
        ProductResponse response = new ProductResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setCategory(categoryName);
        response.setPrice(entity.getPrice().floatValue());
        response.setCurrency(ProductResponse.CurrencyEnum.valueOf(entity.getCurrency()));
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
}
