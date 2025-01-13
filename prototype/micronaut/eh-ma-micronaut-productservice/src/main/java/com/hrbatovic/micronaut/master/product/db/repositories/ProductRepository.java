package com.hrbatovic.micronaut.master.product.db.repositories;

import com.hrbatovic.micronaut.master.product.db.entities.ProductEntity;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface ProductRepository extends PageableRepository<ProductEntity, UUID> {

    default List<ProductEntity> queryProducts(Integer page, Integer limit, String search, UUID categoryId, Float priceMin, Float priceMax, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

        List<Document> conditions = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String regexPattern = "^.*" + Pattern.quote(search) + ".*$";
            conditions.add(new Document("title", new Document("$regex", regexPattern).append("$options", "i")));
        }

        if (categoryId != null) {
            conditions.add(new Document("categoryId", categoryId));
        }

        if (priceMin != null) {
            conditions.add(new Document("price", new Document("$gte", BigDecimal.valueOf(priceMin))));
        }
        if (priceMax != null) {
            conditions.add(new Document("price", new Document("$lte", BigDecimal.valueOf(priceMax))));
        }

        if (createdAfter != null) {
            conditions.add(new Document("createdAt", new Document("$gte", createdAfter)));
        }
        if (createdBefore != null) {
            conditions.add(new Document("createdAt", new Document("$lte", createdBefore)));
        }

        Sort sortOrder = resolveSort(sort);

        Pageable pageable = Pageable.from(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                sortOrder
        );

        if (!conditions.isEmpty()) {
            return findByCustomFilter(conditions, pageable);
        }

        return findAll(new ArrayList<>(), pageable);
    }

    // Custom method to execute queries with filters
    @MongoFindQuery("{ $and: :filter }")
    List<ProductEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    // Method to fetch all products without any filter
    @MongoFindQuery("{}")
    List<ProductEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "priceAsc":
                return Sort.of(Sort.Order.asc("price"));
            case "priceDesc":
                return Sort.of(Sort.Order.desc("price"));
            case "dateAsc":
                return Sort.of(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.of(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }
}