package com.hrbatovic.micronaut.master.tracking.api;

import com.hrbatovic.micronaut.master.tracking.ApiInputValidator;
import com.hrbatovic.micronaut.master.tracking.db.entities.EventEntity;
import com.hrbatovic.micronaut.master.tracking.db.repositories.EventRepository;
import com.hrbatovic.micronaut.master.tracking.mapper.MapUtil;
import com.hrbatovic.micronaut.master.tracking.model.Event;
import com.hrbatovic.micronaut.master.tracking.model.EventListResponse;
import com.hrbatovic.micronaut.master.tracking.model.EventListResponsePagination;
import com.hrbatovic.micronaut.master.tracking.model.ListEventsSortParameter;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class EventApiService implements EventsApi {

    @Inject
    EventRepository eventRepository;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin"})
    public Event getEventById(UUID eventId) {
        ApiInputValidator.validateEventId(eventId);
        EventEntity eventEntity = eventRepository.findById(eventId).orElse(null);
        if (eventEntity == null) {
            throw new RuntimeException("Event not found for ID: " + eventId);
        }

        return mapper.map(eventEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId, ListEventsSortParameter sort) {
        String sortString = sort != null ? sort.toString() : null;

        List<EventEntity> eventEntities = eventRepository.queryEvents(
                page,
                limit,
                eventType,
                sourceService,
                userId,
                userEmail,
                sessionId,
                productId,
                orderId,
                occurredAfter,
                occurredBefore,
                requestCorrelationId,
                sortString
        );

        long totalItems = eventEntities.size();
        int totalPages = (int) Math.ceil((double) totalItems / (limit != null ? limit : 10));

        EventListResponsePagination pagination = new EventListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        return new EventListResponse()
                .events(mapper.map(eventEntities))
                .pagination(pagination);
    }
}
