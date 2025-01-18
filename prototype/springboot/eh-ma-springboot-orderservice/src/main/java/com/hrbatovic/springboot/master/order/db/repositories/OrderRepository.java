package com.hrbatovic.springboot.master.order.db.repositories;

import com.hrbatovic.springboot.master.order.db.entities.OrderEntity;
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
public interface OrderRepository extends MongoRepository<OrderEntity, UUID> {

    default List<OrderEntity> queryUserOrders(UUID userId, Integer page, Integer limit, String status, String sort) {
        List<Document> conditions = new ArrayList<>();

        conditions.add(new Document("userId", userId));

        if (status != null && !status.isEmpty()) {
            conditions.add(new Document("status", status));
        }

        Sort sortOrder = resolveSort(sort);

        Pageable pageable = PageRequest.of(
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

        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                sortOrder
        );

        if (!conditions.isEmpty()) {
            return findByCustomFilter(conditions, pageable);
        }

        return findAllOrders(new ArrayList<>(), pageable);
    }

    @Query(value = "{ $and: ?0 }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<OrderEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value = "{}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<OrderEntity> findAllOrders(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "dateAsc":
                return Sort.by(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.by(Sort.Order.desc("createdAt"));
            case "amountAsc":
                return Sort.by(Sort.Order.asc("totalAmount"));
            case "amountDesc":
                return Sort.by(Sort.Order.desc("totalAmount"));
            default:
                return Sort.unsorted();
        }
    }

}
