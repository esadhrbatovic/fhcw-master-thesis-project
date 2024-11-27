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

    @NotNull
    @BsonProperty("title")
    private String title;

    @NotNull
    @BsonProperty("description")
    private String description;

    @NotNull
    @BsonProperty("categoryId")
    private UUID categoryId;

    @NotNull
    @BsonProperty("price")
    private BigDecimal price;

    @NotNull
    @BsonProperty("currency")
    private String currency;

    @NotNull
    @BsonProperty("createdAt")
    private LocalDateTime createdAt;

    @NotNull
    @BsonProperty("updatedAt")
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

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, categoryId, price, currency, createdAt, updatedAt);
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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}