package com.hrbatovic.micronaut.master.notification.db.repositories;

import com.hrbatovic.micronaut.master.notification.db.entities.NotificationEntity;
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
public interface NotificationRepository extends PageableRepository<NotificationEntity, UUID> {

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
    List<NotificationEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @MongoFindQuery("{}")
    List<NotificationEntity> findAll(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        return switch (sort) {
            case "dateAsc" -> Sort.of(Sort.Order.asc("sentAt"));
            case "dateDesc" -> Sort.of(Sort.Order.desc("sentAt"));
            default -> Sort.unsorted();
        };
    }
}
