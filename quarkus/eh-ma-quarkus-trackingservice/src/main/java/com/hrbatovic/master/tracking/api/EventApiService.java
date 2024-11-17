package com.hrbatovic.master.tracking.api;

import com.hrbatovic.master.quarkus.tracking.api.EventsApi;
import com.hrbatovic.master.quarkus.tracking.model.EventListResponse;
import com.hrbatovic.master.quarkus.tracking.model.EventResponse;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class EventApiService implements EventsApi {

    @Override
    public EventResponse getEventById(UUID eventId) {
        return null;
    }

    @Override
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, String sort) {
        return null;
    }
}
