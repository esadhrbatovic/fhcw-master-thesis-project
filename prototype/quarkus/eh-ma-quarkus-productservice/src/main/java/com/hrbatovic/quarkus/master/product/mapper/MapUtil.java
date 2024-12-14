package com.hrbatovic.quarkus.master.product.mapper;

import com.hrbatovic.master.quarkus.product.model.*;
import com.hrbatovic.quarkus.master.product.db.entities.CategoryEntity;
import com.hrbatovic.quarkus.master.product.db.entities.ProductEntity;
import com.hrbatovic.quarkus.master.product.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.product.messaging.model.in.UserRegisteredEvent;
import com.hrbatovic.quarkus.master.product.messaging.model.in.UserUpdatedEvent;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract List<Product> map(List<ProductEntity> productEntityList, @Context Map<UUID, String> categoryMap);

    @Mapping(target = "category", ignore = true)
    public abstract Product map(ProductEntity productEntity, @Context Map<UUID, String> categoryMap);

    @Mapping(target = "category", source = "categoryName")
    public abstract ProductResponse map(ProductEntity productEntity, String categoryName);

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
    public abstract void update(@MappingTarget ProductEntity productEntity, UpdateProductRequest updateProductRequest,  CategoryEntity categoryEntity);

    public abstract UserEntity map(UserRegisteredEvent userRegisteredEvent);

    public abstract void update(@MappingTarget UserEntity userEntity, UserUpdatedEvent userUpdatedEvent);
}
