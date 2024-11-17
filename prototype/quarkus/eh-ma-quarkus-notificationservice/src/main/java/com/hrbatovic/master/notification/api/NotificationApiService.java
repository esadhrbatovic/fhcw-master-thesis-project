package com.hrbatovic.master.notification.api;

import com.hrbatovic.master.quarkus.notification.api.NotificationsApi;
import com.hrbatovic.master.quarkus.notification.model.NotificationListResponse;
import com.hrbatovic.master.quarkus.notification.model.NotificationResponse;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestScoped
public class NotificationApiService implements NotificationsApi {

    @Override
    public NotificationResponse getNotificationById(UUID notificationId) {
        return null;
    }

    @Override
    public NotificationListResponse listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {
        return null;
    }
}
