package com.hrbatovic.quarkus.master.notification.db.entities;

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


@MongoEntity(collection = "notifications")
public class NotificationEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private UUID userId;

    private String email;

    private String type;

    private String subject;

    private String body;

    private LocalDateTime sentAt;

    public static PanacheQuery<NotificationEntity> queryNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        if (email != null && !email.isEmpty()) {
            conditions.add("{ 'email': :email }");
            params.put("email", email);
        }
        if (userId != null) {
            conditions.add("{ 'userId': :userId }");
            params.put("userId", userId);
        }
        if (type != null && !type.isEmpty()) {
            conditions.add("{ 'type': :type }");
            params.put("type", type);
        }
        if (sentAfter != null) {
            conditions.add("{ 'sentAt': { '$gte': :sentAfter } }");
            params.put("sentAfter", sentAfter);
        }
        if (sentBefore != null) {
            conditions.add("{ 'sentAt': { '$lte': :sentBefore } }");
            params.put("sentBefore", sentBefore);
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append("{ '$and': [ ").append(String.join(", ", conditions)).append(" ] }");
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = resolveSortOrder(sort);

        PanacheQuery<NotificationEntity> query = sortOrder != null
                ? find(queryBuilder.toString(), sortOrder, params)
                : find(queryBuilder.toString(), params);

        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (limit != null && limit > 0) ? limit : 10;
        query.page(Page.of(pageNumber, pageSize));

        return query;
    }

    private static Sort resolveSortOrder(String sort) {
        if (sort == null || sort.isEmpty()) {
            return null;
        }
        return switch (sort) {
            case "dateAsc" -> Sort.ascending("sentAt");
            case "dateDesc" -> Sort.descending("sentAt");
            default -> null;
        };
    }


    public NotificationEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public NotificationEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public NotificationEntity setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NotificationEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getType() {
        return type;
    }

    public NotificationEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public NotificationEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public NotificationEntity setBody(String body) {
        this.body = body;
        return this;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public NotificationEntity setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("userId", userId)
                .append("email", email)
                .append("type", type)
                .append("subject", subject)
                .append("body", body)
                .append("sentAt", sentAt)
                .toString();
    }
}
