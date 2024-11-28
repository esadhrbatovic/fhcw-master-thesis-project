package com.hrbatovic.quarkus.master.product.db.entities;


import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.validation.constraints.NotNull;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class ProductEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private String title;

    private String description;

    private UUID categoryId;

    private BigDecimal price;

    private String currency;

    private String imageUrl;

    private boolean licenseAvailable;

    private boolean deleted;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ProductEntity(){
        this.id = UUID.randomUUID();
    }

    public static PanacheQuery<ProductEntity> findProducts(Integer page, Integer limit, String search, String category, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        List<String> conditions = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String regexPattern = ".*" + Pattern.quote(search) + ".*";
            conditions.add("{ 'title': { '$regex': :searchPattern, '$options': 'i' } }");
            params.put("searchPattern", regexPattern);
        }

        if (category != null && !category.isEmpty()) {
            CategoryEntity categoryEntity = CategoryEntity.find("name", category).firstResult();
            if (categoryEntity != null) {
                conditions.add("{ 'categoryId': :categoryId }");
                params.put("categoryId", categoryEntity.getId());
            } else {
                return find("{}").page(Page.of(page != null ? page - 1 : 0, limit != null ? limit : 10));
            }
        }

        if (priceMin != null) {
            conditions.add("{ 'price': { '$gte': :priceMin } }");
            params.put("priceMin", BigDecimal.valueOf(priceMin));
        }

        if (priceMax != null) {
            conditions.add("{ 'price': { '$lte': :priceMax } }");
            params.put("priceMax", BigDecimal.valueOf(priceMax));
        }

        if (createdAfter != null) {
            conditions.add("{ 'createdAt': { '$gte': :createdAfter } }");
            params.put("createdAfter", createdAfter);
        }

        if (createdBefore != null) {
            conditions.add("{ 'createdAt': { '$lte': :createdBefore } }");
            params.put("createdBefore", createdBefore);
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("{ '$and': [ ");
            queryBuilder.append(String.join(", ", conditions));
            queryBuilder.append(" ] }");
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = null;
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "priceAsc":
                    sortOrder = Sort.ascending("price");
                    break;
                case "priceDesc":
                    sortOrder = Sort.descending("price");
                    break;
                case "dateAsc":
                    sortOrder = Sort.ascending("createdAt");
                    break;
                case "dateDesc":
                    sortOrder = Sort.descending("createdAt");
                    break;
                default:
                    break;
            }
        }

        PanacheQuery<ProductEntity> query;
        if (sortOrder != null) {
            query = find(queryBuilder.toString(), sortOrder, params);
        } else {
            query = find(queryBuilder.toString(), params);
        }

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 10;
        }

        query.page(Page.of(page - 1, limit));
        return query;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isLicenseAvailable() {
        return licenseAvailable;
    }

    public void setLicenseAvailable(boolean licenseAvailable) {
        this.licenseAvailable = licenseAvailable;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", licenseAvailable=" + licenseAvailable +
                ", deleted=" + deleted +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}