package com.hrbatovic.springboot.master.notification.db.repositories;

import com.hrbatovic.springboot.master.notification.db.entities.NotificationEntity;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationEntity, UUID> {

    default List<NotificationEntity> queryNotifications(
            Integer page,
            Integer limit,
            String email,
            UUID userId,
            String type,
            LocalDateTime sentAfter,
            LocalDateTime sentBefore,
            String sort
    ) {
        List<Document> conditions = new ArrayList<>();

        // Add filters
        if (email != null && !email.isEmpty()) {
            conditions.add(new Document("email", email));
        }
        if (userId != null) {
            conditions.add(new Document("userId", userId));
        }
        if (type != null && !type.isEmpty()) {
            conditions.add(new Document("type", type));
        }
        if (sentAfter != null) {
            conditions.add(new Document("sentAt", new Document("$gte", sentAfter)));
        }
        if (sentBefore != null) {
            conditions.add(new Document("sentAt", new Document("$lte", sentBefore)));
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

    @Query(value= "{ $and: ?0 }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<NotificationEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value = "{}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<NotificationEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        return switch (sort) {
            case "dateAsc" -> Sort.by(Sort.Order.asc("sentAt"));
            case "dateDesc" -> Sort.by(Sort.Order.desc("sentAt"));
            default -> Sort.unsorted();
        };
    }
}
