package com.hrbatovic.springboot.master.product.db.repositories;

import com.hrbatovic.springboot.master.product.db.entities.ProductEntity;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, UUID> {

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

        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                sortOrder
        );

        if (!conditions.isEmpty()) {
            return findByCustomFilter(conditions, pageable);
        }

        return findAll(new ArrayList<>(), pageable);
    }

    @Query(value = "{ '$and': ?0 }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<ProductEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value = "{}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<ProductEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "priceAsc":
                return Sort.by(Sort.Order.asc("price"));
            case "priceDesc":
                return Sort.by(Sort.Order.desc("price"));
            case "dateAsc":
                return Sort.by(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.by(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }

}
