package com.hrbatovic.springboot.master.notification.db.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collation = "notifications")
public class NotificationEntity {

    @Id
    private UUID id;

    private UUID userId;

    private String email;

    private String type;

    private String subject;

    private String body;

    private LocalDateTime sentAt;

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
