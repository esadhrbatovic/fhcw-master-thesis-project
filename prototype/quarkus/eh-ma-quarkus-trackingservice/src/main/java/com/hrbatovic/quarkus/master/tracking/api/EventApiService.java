package com.hrbatovic.quarkus.master.tracking.api;

import com.hrbatovic.master.quarkus.tracking.api.EventsApi;
import com.hrbatovic.master.quarkus.tracking.model.Event;
import com.hrbatovic.master.quarkus.tracking.model.EventListResponse;
import com.hrbatovic.quarkus.master.tracking.db.entities.EventEntity;
import com.hrbatovic.quarkus.master.tracking.mapper.MapUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class EventApiService implements EventsApi {

    @Inject
    MapUtil mapper;

    @Override
    public Event getEventById(UUID eventId) {
        return null;
    }

    @Override
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, String sort) {
        List<EventEntity> eventEntities = EventEntity.listAll();
        return new EventListResponse().events(mapper.map(eventEntities));
    }

}
