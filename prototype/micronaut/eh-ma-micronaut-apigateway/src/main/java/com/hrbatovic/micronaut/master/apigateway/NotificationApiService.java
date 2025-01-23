package com.hrbatovic.micronaut.master.apigateway;

import com.hrbatovic.micronaut.master.apigateway.api.NotificationsApi;
import com.hrbatovic.micronaut.master.apigateway.mapper.MapUtil;
import com.hrbatovic.micronaut.master.apigateway.model.ListLicensesSortParameter;
import com.hrbatovic.micronaut.master.apigateway.model.NotificationListResponse;
import com.hrbatovic.micronaut.master.apigateway.model.NotificationResponse;
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
public class NotificationApiService implements NotificationsApi {

    @Inject
    MapUtil mapper;

    @Inject
    com.hrbatovic.master.micronaut.apigateway.clients.notification.api.NotificationsApi notificationsApiClient;

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public NotificationResponse getNotificationById(UUID notificationId, String authorization) {
        return mapper.map(notificationsApiClient.getNotificationById(notificationId, authorization));
    }

    @Override
    @ExecuteOn(TaskExecutors.BLOCKING)
    @RolesAllowed({"admin"})
    public NotificationListResponse listNotifications(String authorization, Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, ListLicensesSortParameter sort) {
        return mapper.map(notificationsApiClient.listNotifications(authorization, page, limit, email, userId, type, sentAfter, sentBefore, mapper.toNotificationSort(sort)));
    }
}
