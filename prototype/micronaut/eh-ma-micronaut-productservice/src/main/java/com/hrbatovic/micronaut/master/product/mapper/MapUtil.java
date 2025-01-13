package com.hrbatovic.micronaut.master.product.mapper;

import com.hrbatovic.micronaut.master.product.db.entities.CategoryEntity;
import com.hrbatovic.micronaut.master.product.db.entities.ProductEntity;
import com.hrbatovic.micronaut.master.product.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.product.messaging.model.in.payload.UserPayload;
import com.hrbatovic.micronaut.master.product.messaging.model.out.payload.ProductPayload;
import com.hrbatovic.micronaut.master.product.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    public static MapUtil INSTANCE = Mappers.getMapper(MapUtil.class);

    @Mapping(target = "category", ignore = true)
    public abstract Product mapAdmin(ProductEntity productEntity, @Context Map<UUID, String> categoryMap);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    public abstract Product mapNonAdmin(ProductEntity productEntity, @Context Map<UUID, String> categoryMap);

    public List<Product> mapAdminList(List<ProductEntity> productEntityList, @Context Map<UUID, String> categoryMap) {
        return productEntityList.stream()
                .map(productEntity -> mapAdmin(productEntity, categoryMap))
                .collect(Collectors.toList());
    }

    public List<Product> mapNonAdminList(List<ProductEntity> productEntityList, @Context Map<UUID, String> categoryMap) {
        return productEntityList.stream()
                .map(productEntity -> mapNonAdmin(productEntity, categoryMap))
                .collect(Collectors.toList());
    }
    @Mapping(target = "category", source = "categoryName")
    public abstract ProductResponse mapAdmin(ProductEntity productEntity, String categoryName);

    @Mapping(target = "category", source = "categoryName")
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract ProductResponse mapNonAdmin(ProductEntity productEntity, String categoryName);

    @AfterMapping
    protected void setCategoryFromMap(@MappingTarget Product product, ProductEntity productEntity, @Context Map<UUID, String> categoryMap) {
        if (categoryMap != null && productEntity.getCategoryId() != null) {
            product.setCategory(categoryMap.get(productEntity.getCategoryId()));
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "currency", constant = "EUR")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "licenseAvailable", constant = "false")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "updatedAt", ignore = true)
    public abstract ProductEntity map(CreateProductRequest createProductRequest, UUID id);

    public abstract Category map(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currency", constant = "EUR")
    @Mapping(target = "categoryId", source = "categoryEntity.id")
    @Mapping(target = "licenseAvailable", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract void update(@MappingTarget ProductEntity productEntity, UpdateProductRequest updateProductRequest, CategoryEntity categoryEntity);

    public abstract UserEntity map(UserPayload userPayload);

    public abstract void update(@MappingTarget UserEntity userEntity, UserPayload userPayload);

    public abstract ProductPayload map(ProductEntity productEntity);
}
