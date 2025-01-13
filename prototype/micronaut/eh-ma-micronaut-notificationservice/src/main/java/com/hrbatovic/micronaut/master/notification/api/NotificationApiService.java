package com.hrbatovic.micronaut.master.notification.api;

import com.hrbatovic.micronaut.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.micronaut.master.notification.db.repositories.NotificationRepository;
import com.hrbatovic.micronaut.master.notification.mapper.MapUtil;
import com.hrbatovic.micronaut.master.notification.model.ListNotificationsSortParameter;
import com.hrbatovic.micronaut.master.notification.model.NotificationListResponse;
import com.hrbatovic.micronaut.master.notification.model.NotificationListResponsePagination;
import com.hrbatovic.micronaut.master.notification.model.NotificationResponse;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@Singleton
public class NotificationApiService implements NotificationsApi {

    @Inject
    NotificationRepository notificationRepository;

    @Override
    @RolesAllowed({"admin"})
    public NotificationResponse getNotificationById(UUID notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElse(null);

        if(notificationEntity == null){
            throw new RuntimeException("Notification not found");
        }

        return MapUtil.INSTANCE.map(notificationEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public NotificationListResponse listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, ListNotificationsSortParameter sort) {
        String sortString = sort != null ? sort.toString() : null;

        List<NotificationEntity> notificationEntities = notificationRepository.queryNotifications(
                page, limit, email, userId, type, sentAfter, sentBefore, sortString
        );

        long totalItems = notificationEntities.size();
        int totalPages = (int) Math.ceil((double) totalItems / (limit != null ? limit : 10));

        NotificationListResponse response = new NotificationListResponse();
        response.setNotifications(MapUtil.INSTANCE.map(notificationEntities));

        NotificationListResponsePagination pagination = new NotificationListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        response.setPagination(pagination);
        return response;
    }
}
