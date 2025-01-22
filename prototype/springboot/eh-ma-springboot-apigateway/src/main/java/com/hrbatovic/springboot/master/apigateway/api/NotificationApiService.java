package com.hrbatovic.springboot.master.apigateway.api;

import com.hrbatovic.master.springboot.gateway.api.NotificationsApi;
import com.hrbatovic.master.springboot.gateway.model.NotificationListResponse;
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
public class NotificationApiService implements NotificationsApi {

    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public NotificationResponse getNotificationById(UUID notificationId) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.notification.api.NotificationsApi notificationsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.notification.api.NotificationsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                notificationsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                NotificationResponse response = mapper.map(notificationsApiClient.getNotificationById(notificationId));
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
    public NotificationListResponse listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {
        try {
            com.hrbatovic.master.springboot.apigateway.clients.notification.api.NotificationsApi notificationsApiClient = new com.hrbatovic.master.springboot.apigateway.clients.notification.api.NotificationsApi();
            org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthentication) {
                JwtAuthentication jwtAuth = (JwtAuthentication) authentication;
                notificationsApiClient.getApiClient().addDefaultHeader("Authorization", "Bearer " + jwtAuth.getJwtToken());
                NotificationListResponse response = mapper.map(notificationsApiClient.listNotifications(page, limit, email, userId, type, sentAfter, sentBefore, sort));
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
