package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.gateway.api.EventsApi;
import com.hrbatovic.master.springboot.gateway.model.Event;
import com.hrbatovic.master.springboot.gateway.model.EventListResponse;
import com.hrbatovic.master.springboot.gateway.model.NotificationResponse;
import com.hrbatovic.springboot.master.apigateway.JwtAuthentication;
import com.hrbatovic.springboot.master.apigateway.exceptions.EhMaException;
import com.hrbatovic.springboot.master.apigateway.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.hrbatovic.springboot.master.apigateway.JsonErrorParser.parseErrorMessage;

@RestController
public class EventsApiService implements EventsApi {
    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Event getEventById(UUID eventId) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.tracking.api.EventsApi eventsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.tracking.api.EventsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                eventsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                Event response = mapper.map(eventsApiClient.getEventById(eventId));
                return response;
            }
            return null;
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EventListResponse listEvents(Integer page, Integer limit, String eventType, String sourceService, UUID userId, String userEmail, UUID sessionId, UUID productId, UUID orderId, LocalDateTime occurredAfter, LocalDateTime occurredBefore, UUID requestCorrelationId, String sort) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.tracking.api.EventsApi eventsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.tracking.api.EventsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                eventsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                EventListResponse response = mapper.map(eventsApiClient.listEvents(page, limit, eventType, sourceService, userId, userEmail, sessionId, productId, orderId, occurredAfter, occurredBefore, requestCorrelationId, sort));
                return response;
            }
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errorMessage = e.getResponseBodyAsString();
                String parsedMessage = parseErrorMessage(errorMessage);
                throw new EhMaException(400, parsedMessage);
            }
            throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
