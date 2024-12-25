package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.EventsApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.Event;
import com.hrbat.quarkus.master.apigateway.model.EventListResponse;
import com.hrbat.quarkus.master.apigateway.model.tracking.api.EventsTrackingRestClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class TrackingApiService implements EventsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    EventsTrackingRestClient eventsTrackingRestClient;

    @Override
    @RolesAllowed({"admin"})
    public Event getEventById(UUID eventId) {
        return mapper.map(eventsTrackingRestClient.getEventById(eventId));
    }

    @Override
    @RolesAllowed({"admin"})
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId, String sort) {
        return mapper.map(eventsTrackingRestClient.listEvents(page, limit, eventType, sourceService, userId, userEmail, sessionId, productId, orderId, occurredAfter, occurredBefore, requestCorrelationId, sort));
    }
}
