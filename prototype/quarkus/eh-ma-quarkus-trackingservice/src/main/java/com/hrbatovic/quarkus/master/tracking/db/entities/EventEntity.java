package com.hrbatovic.quarkus.master.tracking.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDateTime;
import java.util.*;

@MongoEntity(collection = "events")
public class EventEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private String eventType;

    private String body;

    private MetadataEntity metadata;

    public static PanacheQuery<EventEntity> queryEvents(
            Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail,
            UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, String sort
    ) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder();
        List<String> conditions = new ArrayList<>();

        if (eventType != null) {
            conditions.add("{ 'eventType': :eventType }");
            params.put("eventType", eventType);
        }
        if (sourceService != null) {
            conditions.add("{ 'metadata.sourceService': :sourceService }");
            params.put("sourceService", sourceService);
        }
        if (userId != null) {
            conditions.add("{ 'metadata.userId': :userId }");
            params.put("userId", userId);
        }
        if (userEmail != null) {
            conditions.add("{ 'metadata.userMail': :userEmail }");
            params.put("userEmail", userEmail);
        }
        if (sessionId != null) {
            conditions.add("{ 'metadata.sessionId': :sessionId }");
            params.put("sessionId", sessionId);
        }
        if (productId != null) {
            conditions.add("{ 'metadata.productId': :productId }");
            params.put("productId", productId);
        }
        if (orderId != null) {
            conditions.add("{ 'metadata.orderId': :orderId }");
            params.put("orderId", orderId);
        }
        if (occurredAfter != null) {
            conditions.add("{ 'metadata.timestamp': { '$gte': :occurredAfter } }");
            params.put("occurredAfter", occurredAfter);
        }
        if (occurredBefore != null) {
            conditions.add("{ 'metadata.timestamp': { '$lte': :occurredBefore } }");
            params.put("occurredBefore", occurredBefore);
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("{ '$and': [ ").append(String.join(", ", conditions)).append(" ] }");
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = null;
        if (sort != null) {
            sortOrder = switch (sort) {
                case "dateAsc" -> Sort.ascending("metadata.timestamp");
                case "dateDesc" -> Sort.descending("metadata.timestamp");
                default -> null;
            };
        }

        PanacheQuery<EventEntity> query = sortOrder != null
                ? find(queryBuilder.toString(), sortOrder, params)
                : find(queryBuilder.toString(), params);

        Page pagination = Page.of((page != null && page > 0) ? page - 1 : 0, (limit != null && limit > 0) ? limit : 10);
        query.page(pagination);

        return query;
    }

    public EventEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public EventEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public EventEntity setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getBody() {
        return body;
    }

    public EventEntity setBody(String body) {
        this.body = body;
        return this;
    }

    public MetadataEntity getMetadata() {
        return metadata;
    }

    public EventEntity setMetadata(MetadataEntity metadata) {
        this.metadata = metadata;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("eventType", eventType)
                .append("body", body)
                .append("metadata", metadata)
                .toString();
    }
}
