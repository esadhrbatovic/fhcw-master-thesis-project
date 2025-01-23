package com.hrbatovic.springboot.master.notification;

import com.hrbatovic.springboot.master.notification.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {
    public static void validateNotificationId(UUID notificationId) {
        if(notificationId == null || StringUtils.isEmpty(notificationId.toString())){
            throw new EhMaException(400, "Notification id must not be empty");
        }
    }
}
