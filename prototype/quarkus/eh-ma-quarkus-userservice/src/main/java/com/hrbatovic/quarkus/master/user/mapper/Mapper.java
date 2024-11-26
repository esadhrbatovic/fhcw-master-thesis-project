package com.hrbatovic.quarkus.master.user.mapper;

import com.hrbatovic.master.quarkus.user.model.Address;
import com.hrbatovic.master.quarkus.user.model.UpdateUserProfileRequest;
import com.hrbatovic.master.quarkus.user.model.UserProfileResponse;
import com.hrbatovic.quarkus.master.user.db.entities.AddressEntity;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.messaging.model.AddressPayload;
import com.hrbatovic.quarkus.master.user.messaging.model.UserUpdateMsgPayload;

import java.time.LocalDateTime;

public abstract class Mapper {

    public static UserProfileResponse map(UserEntity userEntity) {
        if(userEntity == null){
            return null;
        }

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setAddress(map(userEntity.getAddress()));
        userProfileResponse.setEmail(userEntity.getEmail());
        userProfileResponse.setId(userEntity.getId());
        userProfileResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userProfileResponse.setFirstName(userEntity.getFirstName());
        userProfileResponse.setLastName(userEntity.getLastName());
        userProfileResponse.setCreatedAt(userEntity.getCreatedAt());
        userProfileResponse.setUpdatedAt(userEntity.getUpdatedAt());
        return userProfileResponse;
    }

    public static Address map(AddressEntity addressEntity) {
        if(addressEntity == null){
            return null;
        }

        Address address = new Address();
        address.setCity(addressEntity.getCity());
        address.setCountry(addressEntity.getCountry());
        address.setStreet(addressEntity.getStreet());
        address.setPostalCode(addressEntity.getPostalCode());
        address.setState(addressEntity.getState());

        return address;
    }


    public static void update(UserEntity userEntity, UpdateUserProfileRequest updateUserProfileRequest) {
        userEntity.setUpdatedAt(LocalDateTime.now());

        if(updateUserProfileRequest.getAddress() != null){
            if(userEntity.getAddress() == null){
                userEntity.setAddress(new AddressEntity());
            }

            update(userEntity.getAddress(), updateUserProfileRequest.getAddress());
        }

        if(updateUserProfileRequest.getFirstName() != null){
            userEntity.setFirstName(updateUserProfileRequest.getFirstName());
        }

        if(updateUserProfileRequest.getLastName() != null) {
            userEntity.setLastName(updateUserProfileRequest.getLastName());
        }

        if(updateUserProfileRequest.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(updateUserProfileRequest.getPhoneNumber());
        }

        if(updateUserProfileRequest.getEmail() != null) {
            userEntity.setEmail(updateUserProfileRequest.getEmail());
        }

        //TODO: set role by admin only - extend api
        //userEntity.setRole();
    }

    private static void update(AddressEntity addressEntity, Address address) {
        if(address.getCity() != null){
            addressEntity.setCity(address.getCity());
        }

        if(address.getCountry() != null){
            addressEntity.setCountry(address.getCountry());
        }

        if(address.getState() != null){
            addressEntity.setState(address.getState());
        }

        if(address.getPostalCode() != null){
            addressEntity.setPostalCode(address.getPostalCode());
        }

        if(address.getStreet() != null){
            addressEntity.setStreet(address.getStreet());
        }

    }


    public static UserUpdateMsgPayload map(UpdateUserProfileRequest updateUserProfileRequest) {
        if (updateUserProfileRequest == null) {
            return null;
        }

        UserUpdateMsgPayload payload = new UserUpdateMsgPayload();

        payload.setFirstName(updateUserProfileRequest.getFirstName());
        payload.setLastName(updateUserProfileRequest.getLastName());
        payload.setEmail(updateUserProfileRequest.getEmail());
        payload.setPassword(updateUserProfileRequest.getPassword());

        payload.setPhoneNumber(updateUserProfileRequest.getPhoneNumber());

        if (updateUserProfileRequest.getAddress() != null) {
            payload.setAddress(mapAddress(updateUserProfileRequest.getAddress()));
        }

        return payload;
    }

    private static AddressPayload mapAddress(Address address) {
        if (address == null) {
            return null;
        }

        AddressPayload addressPayload = new AddressPayload();
        addressPayload.setStreet(address.getStreet());
        addressPayload.setCity(address.getCity());
        addressPayload.setState(address.getState());
        addressPayload.setPostalCode(address.getPostalCode());
        addressPayload.setCountry(address.getCountry());

        return addressPayload;
    }

}
