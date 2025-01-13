package com.hrbatovic.micronaut.master.order.db.repositories;

import com.hrbatovic.micronaut.master.order.db.entities.OrderEntity;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface OrderRepository extends PageableRepository<OrderEntity, UUID> {

    default List<OrderEntity> queryUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {
        List<Document> conditions = new ArrayList<>();

        // Add user ID condition
        conditions.add(new Document("userId", userId));

        // Add status condition
        if (status != null && !status.isEmpty()) {
            conditions.add(new Document("status", status));
        }

        Sort sortOrder = resolveSort(sort);

        Pageable pageable = Pageable.from(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                sortOrder
        );

        return findByCustomFilter(conditions, pageable);

    }

    default List<OrderEntity> queryOrders(Integer page, Integer limit, String status, String sort) {
        List<Document> conditions = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            conditions.add(new Document("status", status));
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

        return findAllOrders(new ArrayList<>(), pageable);
    }

    @MongoFindQuery("{ $and: :filter }")
    List<OrderEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @MongoFindQuery("{}")
    List<OrderEntity> findAllOrders(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "dateAsc":
                return Sort.of(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.of(Sort.Order.desc("createdAt"));
            case "amountAsc":
                return Sort.of(Sort.Order.asc("totalAmount"));
            case "amountDesc":
                return Sort.of(Sort.Order.desc("totalAmount"));
            default:
                return Sort.unsorted();
        }
    }
}
