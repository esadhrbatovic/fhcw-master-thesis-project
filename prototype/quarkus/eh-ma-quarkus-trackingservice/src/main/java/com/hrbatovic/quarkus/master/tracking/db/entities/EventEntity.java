package com.hrbatovic.quarkus.master.tracking.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@MongoEntity(collection = "events")
public class EventEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private String eventType;

    private String body;

    private MetadataEntity metadata;

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
