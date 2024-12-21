package com.hrbatovic.quarkus.master.tracking.api.validators;

import com.hrbatovic.quarkus.master.tracking.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {

    public static void validateEventId(UUID eventId) {
        if(eventId == null || StringUtils.isEmpty(eventId.toString())){
            throw new EhMaException(400, "Event id must not be null");
        }
    }
}
