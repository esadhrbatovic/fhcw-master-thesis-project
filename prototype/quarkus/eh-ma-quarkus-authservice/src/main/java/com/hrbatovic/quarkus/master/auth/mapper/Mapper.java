package com.hrbatovic.quarkus.master.auth.mapper;

import com.hrbatovic.quarkus.master.auth.db.entities.AddressEntity;
import com.hrbatovic.quarkus.master.auth.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.auth.messaging.model.AddressPayload;
import com.hrbatovic.quarkus.master.auth.messaging.model.UserUpdateMsgPayload;

public abstract class Mapper {


    public static UserEntity update(UserUpdateMsgPayload userUpdateMsgPayload, UserEntity userEntity) {
        if (userUpdateMsgPayload == null || userEntity == null) {
            return userEntity;
        }

        if (userUpdateMsgPayload.getFirstName() != null) {
            userEntity.setFirstName(userUpdateMsgPayload.getFirstName());
        }

        if (userUpdateMsgPayload.getLastName() != null) {
            userEntity.setLastName(userUpdateMsgPayload.getLastName());
        }

        if (userUpdateMsgPayload.getEmail() != null) {
            userEntity.setEmail(userUpdateMsgPayload.getEmail());
        }

        if (userUpdateMsgPayload.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(userUpdateMsgPayload.getPhoneNumber());
        }

        if (userUpdateMsgPayload.getRole() != null) {
            userEntity.setRole(userUpdateMsgPayload.getRole());
        }

        if (userUpdateMsgPayload.getAddress() != null) {
            AddressPayload addressPayload = userUpdateMsgPayload.getAddress();
            AddressEntity addressEntity = userEntity.getAddress();

            if (addressEntity == null) {
                addressEntity = new AddressEntity();
                userEntity.setAddress(addressEntity);
            }

            if (addressPayload.getStreet() != null) {
                addressEntity.setStreet(addressPayload.getStreet());
            }

            if (addressPayload.getCity() != null) {
                addressEntity.setCity(addressPayload.getCity());
            }

            if (addressPayload.getState() != null) {
                addressEntity.setState(addressPayload.getState());
            }

            if (addressPayload.getPostalCode() != null) {
                addressEntity.setPostalCode(addressPayload.getPostalCode());
            }

            if (addressPayload.getCountry() != null) {
                addressEntity.setCountry(addressPayload.getCountry());
            }
        }

        return userEntity;
    }



}
