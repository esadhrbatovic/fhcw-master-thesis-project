package com.hrbatovic.springboot.master.tracking.api;

import com.hrbatovic.master.springboot.tracking.api.EventsApi;
import com.hrbatovic.master.springboot.tracking.model.Event;
import com.hrbatovic.master.springboot.tracking.model.EventListResponse;
import com.hrbatovic.master.springboot.tracking.model.EventListResponsePagination;
import com.hrbatovic.springboot.master.tracking.ApiInputValidator;
import com.hrbatovic.springboot.master.tracking.db.entities.EventEntity;
import com.hrbatovic.springboot.master.tracking.db.repositories.EventRepository;
import com.hrbatovic.springboot.master.tracking.exceptions.EhMaException;
import com.hrbatovic.springboot.master.tracking.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class EventApiService implements EventsApi {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MapUtil mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Event getEventById(UUID eventId) {
        ApiInputValidator.validateEventId(eventId);
        EventEntity eventEntity = eventRepository.findById(eventId).orElse(null);
        if (eventEntity == null) {
            throw new EhMaException(404, "Event not found for ID: " + eventId);
        }

        return mapper.map(eventEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId, String sort) {
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
