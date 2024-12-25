package com.hrbatovic.quarkus.master.tracking.api;

import com.hrbatovic.master.quarkus.tracking.api.EventsApi;
import com.hrbatovic.master.quarkus.tracking.model.Event;
import com.hrbatovic.master.quarkus.tracking.model.EventListResponse;
import com.hrbatovic.master.quarkus.tracking.model.EventListResponsePagination;
import com.hrbatovic.quarkus.master.tracking.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.tracking.db.entities.EventEntity;
import com.hrbatovic.quarkus.master.tracking.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class EventApiService implements EventsApi {

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin"})
    public Event getEventById(UUID eventId) {
        ApiInputValidator.validateEventId(eventId);

        EventEntity eventEntity = EventEntity.findById(eventId);
        if (eventEntity == null) {
            throw new RuntimeException("Event not found for ID: " + eventId);
        }
        return mapper.map(eventEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public EventListResponse listEvents(
            Integer page, Integer limit, String eventType, String sourceService,
            UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId,
            LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId,String sort
    ) {

        PanacheQuery<EventEntity> query = EventEntity.queryEvents(
                page, limit, eventType, sourceService, userId, userEmail, sessionId, productId, orderId, occurredAfter, occurredBefore, requestCorrelationId, sort
        );

        List<EventEntity> eventEntities = query.list();
        long totalItems = query.count();
        int totalPages = query.pageCount();


        EventListResponsePagination pagination = new EventListResponsePagination()
                .currentPage(page != null && page > 0 ? page : 1)
                .limit(limit != null && limit > 0 ? limit : 10)
                .totalItems((int) totalItems)
                .totalPages(totalPages);

        return new EventListResponse()
                .events(mapper.map(eventEntities))
                .pagination(pagination);
    }
}
