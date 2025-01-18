package com.hrbatovic.springboot.master.tracking.db.repositories;

import com.hrbatovic.springboot.master.tracking.db.entities.EventEntity;
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
public interface EventRepository extends MongoRepository<EventEntity, UUID> {

    default List<EventEntity> queryEvents(
            Integer page,
            Integer limit,
            String eventType,
            String sourceService,
            UUID userId,
            String userEmail,
            UUID sessionId,
            UUID productId,
            UUID orderId,
            LocalDateTime occurredAfter,
            LocalDateTime occurredBefore,
            UUID requestCorrelationId,
            String sort
    ) {
        List<Document> conditions = new ArrayList<>();

        if (eventType != null && !eventType.isEmpty()) {
            conditions.add(new Document("eventType", eventType));
        }
        if (sourceService != null && !sourceService.isEmpty()) {
            conditions.add(new Document("metadata.sourceService", sourceService));
        }
        if (userId != null) {
            conditions.add(new Document("metadata.userId", userId));
        }
        if (userEmail != null && !userEmail.isEmpty()) {
            conditions.add(new Document("metadata.userMail", userEmail));
        }
        if (sessionId != null) {
            conditions.add(new Document("metadata.sessionId", sessionId));
        }
        if (productId != null) {
            conditions.add(new Document("metadata.productId", productId));
        }
        if (orderId != null) {
            conditions.add(new Document("metadata.orderId", orderId));
        }
        if (occurredAfter != null) {
            conditions.add(new Document("metadata.timestamp", new Document("$gte", occurredAfter)));
        }
        if (occurredBefore != null) {
            conditions.add(new Document("metadata.timestamp", new Document("$lte", occurredBefore)));
        }
        if (requestCorrelationId != null) {
            conditions.add(new Document("metadata.requestCorrelationId", requestCorrelationId));
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
        return findAllEvents(new ArrayList<>(), pageable);
    }

    @Query(value = "{ $and: ?0 }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<EventEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value = "{}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<EventEntity> findAllEvents(List<Document> filter, Pageable pageable);

    private Sort resolveSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        return switch (sort) {
            case "dateAsc" -> Sort.by(Sort.Order.asc("metadata.timestamp"));
            case "dateDesc" -> Sort.by(Sort.Order.desc("metadata.timestamp"));
            default -> Sort.unsorted();
        };
    }

}
