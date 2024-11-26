package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.api.UsersApi;
import com.hrbatovic.master.quarkus.user.model.*;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.mapper.Mapper;
import com.hrbatovic.quarkus.master.user.messaging.model.UserUpdateMsgPayload;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.*;

@RequestScoped
public class UserApiService implements UsersApi {

    @Inject
    @Channel("user-updated-out")
    Emitter<UserUpdateMsgPayload> userUpdatedEmitter;

    @Inject
    @Channel("user-deleted-out")
    Emitter<UUID> userDeletedEmitter;

    @Override
    public UserProfileResponse getUser(UUID id) {
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null){
            throw new RuntimeException("user not found");
        }

        return Mapper.map(userEntity);
    }

    @Override
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

        PanacheQuery<UserEntity> query = UserEntity.findUsers(page, limit, search, createdAfter, createdBefore, sort);

        List<UserEntity> userEntityList = query.list();

        long totalItems = query.count();
        int totalPages = query.pageCount();

        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUsers(map(userEntityList));

        UserListResponsePagination pagination = new UserListResponsePagination();
        pagination.setCurrentPage(page != null ? page : 1);
        pagination.setLimit(limit != null ? limit : 10);
        pagination.setTotalItems((int) totalItems);
        pagination.setTotalPages(totalPages);

        userListResponse.setPagination(pagination);


        return userListResponse;
    }

    @Override
    public UserProfileResponse updateUser(UUID id, UpdateUserProfileRequest updateUserProfileRequest) {
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null ){
            throw new RuntimeException("User not found");
        }

        Mapper.update(userEntity, updateUserProfileRequest);

        userEntity.persistOrUpdate();

        UserUpdateMsgPayload userUpdatePayload = Mapper.map(updateUserProfileRequest);
        userUpdatePayload.setId(id);
        userUpdatedEmitter.send(userUpdatePayload);

        return Mapper.map(userEntity);
    }

    @Override
    public DeleteUserResponse deleteUser(UUID id) {
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null){
            throw new RuntimeException("User not found");
        }

        userEntity.delete();
        userDeletedEmitter.send(id);

        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        deleteUserResponse.setMessage("User deleted successfully");
        return deleteUserResponse;
    }

    private List<UserProfileResponse> map(List<UserEntity> userEntityList) {
        if(userEntityList == null){
            return new ArrayList<>();
        }

        List<UserProfileResponse> userProfileResponseList = new ArrayList<>();
        userEntityList.forEach(u->{
            userProfileResponseList.add(Mapper.map(u));
        });

        return userProfileResponseList;
    }

}

