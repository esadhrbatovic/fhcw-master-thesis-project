package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.model.UpdateUserProfileRequest;
import com.hrbatovic.quarkus.master.user.exceptions.EhMaException;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ApiInputValidator {


    public static void validateUserId(UUID id) {
        if(id == null || StringUtils.isEmpty(id.toString())){
            throw new EhMaException(400, "Id must not be empty.");
        }
    }

    public static void validateUpdateUser(UpdateUserProfileRequest updateUserProfileRequest) {
        if(updateUserProfileRequest == null || StringUtils.isEmpty(updateUserProfileRequest.getFirstName()) ||
        StringUtils.isEmpty(updateUserProfileRequest.getLastName()) ||
        StringUtils.isEmpty(updateUserProfileRequest.getPhoneNumber()) ||
        updateUserProfileRequest.getAddress() == null ||
        StringUtils.isEmpty(updateUserProfileRequest.getAddress().getCity()) ||
                StringUtils.isEmpty(updateUserProfileRequest.getAddress().getCountry()) ||
                StringUtils.isEmpty(updateUserProfileRequest.getAddress().getState()) ||
                StringUtils.isEmpty(updateUserProfileRequest.getAddress().getStreet()) ||
                StringUtils.isEmpty(updateUserProfileRequest.getAddress().getPostalCode()) ||
                StringUtils.isEmpty(updateUserProfileRequest.getAddress().getCity())
        ){
          throw new EhMaException(400, "Not all update user information was provided.");
        }
    }
}
