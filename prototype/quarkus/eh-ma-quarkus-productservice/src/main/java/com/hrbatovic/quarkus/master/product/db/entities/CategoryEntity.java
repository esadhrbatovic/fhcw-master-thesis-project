package com.hrbatovic.quarkus.master.product.db.entities;

import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;
import java.util.UUID;

@MongoEntity(collection = "categories")
public class CategoryEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    @NotNull
    private String name;

    public static PanacheQuery<CategoryEntity> queryCategories(int page, int limit, String search) {
        String query = (search != null && !search.isEmpty())
                ? "{ 'name': { '$regex': :search, '$options': 'i' } }"
                : "{}";

        Map<String, Object> params = new HashMap<>();
        if (search != null && !search.isEmpty()) {
            params.put("search", search);
        }

        PanacheQuery<CategoryEntity> panacheQuery = find(query, params);
        panacheQuery.page(Page.of(page - 1, limit));
        return panacheQuery;
    }

    public CategoryEntity() {
        this.id =UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}