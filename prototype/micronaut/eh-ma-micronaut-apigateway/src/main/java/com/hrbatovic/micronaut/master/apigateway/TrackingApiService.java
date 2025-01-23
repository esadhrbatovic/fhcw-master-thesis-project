package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.EventsApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.Event;
import com.hrbatovic.micronaut.master.apigateway.model.EventListResponse;
import com.hrbatovic.micronaut.master.apigateway.model.ListLicensesSortParameter;
import io.micronaut.http.annotation.Controller;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Singleton
public class TrackingApiService implements EventsApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.tracking.api.EventsApi eventsApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public Event getEventById(UUID eventId, String authorization) {
        return mapper.map(eventsApiClient.getEventById(eventId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public EventListResponse listEvents(String authorization, Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId, ListLicensesSortParameter sort) {
        return mapper.map(eventsApiClient.listEvents(authorization, page, limit, eventType, sourceService, userId, userEmail, sessionId, productId, orderId, occurredAfter, occurredBefore, requestCorrelationId, mapper.toEventSort(sort)));
    }
}
