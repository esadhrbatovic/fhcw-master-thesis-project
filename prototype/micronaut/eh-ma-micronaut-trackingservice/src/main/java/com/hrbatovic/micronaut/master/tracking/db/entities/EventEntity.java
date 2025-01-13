package com.hrbatovic.micronaut.master.tracking.db.entities;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

@Serdeable
@MappedEntity(value = "events")
public class EventEntity {

    @Id
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
