package com.hrbatovic.micronaut.master.license.db.repositories;

import com.hrbatovic.micronaut.master.license.db.entities.LicenseEntity;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface LicenseRepository extends PageableRepository<LicenseEntity, UUID> {

    @MongoFindQuery("{'orderId':  :orderId}")
    List<LicenseEntity> findByOrderid(UUID orderId);

    default List<LicenseEntity> queryLicenses(Integer page, Integer limit, UUID userId, UUID productId, String sort) {
        List<Document> conditions = new ArrayList<>();

        if (userId != null) {
            conditions.add(new Document("userId", userId));
        }

        if (productId != null) {
            conditions.add(new Document("productId", productId));
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

    @MongoFindQuery("{ $and: :filter }")
    List<LicenseEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @MongoFindQuery("{}")
    List<LicenseEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "dateAsc":
                return Sort.of(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.of(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }
}
