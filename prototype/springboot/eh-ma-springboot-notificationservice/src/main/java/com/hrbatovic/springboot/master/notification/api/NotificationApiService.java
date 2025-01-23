package com.hrbatovic.springboot.master.notification.api;

import com.hrbatovic.master.springboot.notification.api.NotificationsApi;
import com.hrbatovic.master.springboot.notification.model.NotificationListResponse;
import com.hrbatovic.master.springboot.notification.model.NotificationListResponsePagination;
import com.hrbatovic.master.springboot.notification.model.NotificationResponse;
import com.hrbatovic.springboot.master.notification.ApiInputValidator;
import com.hrbatovic.springboot.master.notification.db.entities.NotificationEntity;
import com.hrbatovic.springboot.master.notification.db.repositories.NotificationRepository;
import com.hrbatovic.springboot.master.notification.exceptions.EhMaException;
import com.hrbatovic.springboot.master.notification.mapper.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class NotificationApiService implements NotificationsApi {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    MapUtil mapper;


    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public NotificationResponse getNotificationById(UUID notificationId) {
        ApiInputValidator.validateNotificationId(notificationId);

        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElse(null);

        if(notificationEntity == null){
            throw new EhMaException(404, "Notification not found");
        }

        return mapper.map(notificationEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public NotificationListResponse listNotifications(Integer page, Integer limit, String email, UUID userId, String type, LocalDateTime sentAfter, LocalDateTime sentBefore, String sort) {
        String sortString = sort != null ? sort.toString() : null;

        List<NotificationEntity> notificationEntities = notificationRepository.queryNotifications(
                page, limit, email, userId, type, sentAfter, sentBefore, sortString
        );

        long totalItems = notificationEntities.size();
        int totalPages = (int) Math.ceil((double) totalItems / (limit != null ? limit : 10));

        NotificationListResponse response = new NotificationListResponse();
        response.setNotifications(mapper.map(notificationEntities));

        NotificationListResponsePagination pagination = new NotificationListResponsePagination();
        pagination.setCurrentPage((page != null && page > 0) ? page : 1);
        pagination.setLimit((limit != null && limit > 0) ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        response.setPagination(pagination);
        return response;
    }
}
