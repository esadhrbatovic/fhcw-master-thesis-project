package com.hrbat.quarkus.master.apigateway;

import com.hrbat.quarkus.master.apigateway.api.NotificationsApi;
import com.hrbat.quarkus.master.apigateway.mapper.MapUtil;
import com.hrbat.quarkus.master.apigateway.model.NotificationListResponse;
import com.hrbat.quarkus.master.apigateway.model.NotificationResponse;
import com.hrbat.quarkus.master.apigateway.model.notification.api.NotificationsNotificationRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class NotificationApiService implements NotificationsApi {

    @Inject
    MapUtil mapper;

    @Inject
    @RestClient
    NotificationsNotificationRestClient notificationsNotificationRestClient;

    @Override
    public NotificationResponse getNotificationById(UUID notificationId) {
        return mapper.map(notificationsNotificationRestClient.getNotificationById(notificationId));
    }

    @Override
    public NotificationListResponse listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {
        return mapper.map(notificationsNotificationRestClient.listNotifications(page, limit, email, userId, type, sentAfter, sentBefore, sort));
    }
}
