package com.hrbatovic.springboot.master.license.db.repositories;

import com.hrbatovic.springboot.master.license.db.entities.LicenseEntity;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface LicenseRepository extends MongoRepository<LicenseEntity, UUID> {

    @Query(value= "{'orderId':  ?0}")
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

    @Query(value= "{ $and: ?0 }")
    List<LicenseEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value= "{}")
    List<LicenseEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "dateAsc":
                return Sort.by(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.by(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }
}
