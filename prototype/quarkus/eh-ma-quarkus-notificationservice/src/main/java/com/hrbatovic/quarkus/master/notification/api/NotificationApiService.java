package com.hrbatovic.quarkus.master.notification.api;

import com.hrbatovic.master.quarkus.notification.api.NotificationsApi;
import com.hrbatovic.master.quarkus.notification.model.Notification;
import com.hrbatovic.master.quarkus.notification.model.NotificationListResponse;
import com.hrbatovic.master.quarkus.notification.model.NotificationListResponsePagination;
import com.hrbatovic.master.quarkus.notification.model.NotificationResponse;
import com.hrbatovic.quarkus.master.notification.api.validators.ApiInputValidator;
import com.hrbatovic.quarkus.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.quarkus.master.notification.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.notification.mapper.MapUtil;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class NotificationApiService implements NotificationsApi {

    @Inject
    MapUtil mapper;

    @Override
    public Response getNotificationById(UUID notificationId) {
        ApiInputValidator.validateNotificationId(notificationId);

        NotificationEntity notificationEntity = NotificationEntity.findById(notificationId);
        if(notificationEntity == null){
            throw new EhMaException(404, "Notification not found");
        }

        return Response.ok(mapper.map(notificationEntity)).status(200).build();
    }

    @Override
    public Response listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {
        PanacheQuery<NotificationEntity> query = NotificationEntity.queryNotifications(page, limit, email, userId, type, sentAfter, sentBefore, sort);

        List<NotificationEntity> notificationEntities = query.list();
        long totalItems = query.count();
        int totalPages = query.pageCount();

        NotificationListResponse response = new NotificationListResponse();
        response.setNotifications(mapper.map(notificationEntities));

        response.setPagination(new NotificationListResponsePagination()
                .currentPage(page != null && page > 0 ? page : 1)
                .limit(limit != null && limit > 0 ? limit : 10)
                .totalItems((int) totalItems)
                .totalPages(totalPages));

        return Response.ok(response).status(200).build();
    }

}
