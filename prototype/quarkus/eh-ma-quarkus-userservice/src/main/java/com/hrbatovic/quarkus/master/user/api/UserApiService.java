package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.api.UsersApi;
import com.hrbatovic.master.quarkus.user.model.*;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.mapper.MapUtil;
import com.hrbatovic.quarkus.master.user.messaging.model.out.UserUpdatedEvent;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.*;

//TODO: user operations seperate from admin operations because role can only be changed and viewed by admins
@RequestScoped
public class UserApiService implements UsersApi {

    @Inject
    @Channel("user-updated-out")
    Emitter<UserUpdatedEvent> userUpdatedEmitter;

    @Inject
    @Channel("user-deleted-out")
    Emitter<UUID> userDeletedEmitter;

    @Inject
    MapUtil mapper;

    @Override
    public UserProfileResponse getUser(UUID id) {
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null){
            throw new RuntimeException("user not found");
        }

        return mapper.map(userEntity);
    }

    @Override
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

        PanacheQuery<UserEntity> query = UserEntity.findUsers(page, limit, search, createdAfter, createdBefore, sort);

        List<UserEntity> userEntityList = query.list();

        long totalItems = query.count();
        int totalPages = query.pageCount();

        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUsers(mapper.map(userEntityList));

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

        mapper.update(userEntity, updateUserProfileRequest);

        userEntity.persistOrUpdate();

        UserUpdatedEvent userUpdatedEvent = mapper.map(updateUserProfileRequest);
        userUpdatedEvent.setId(userEntity.getId());
        userUpdatedEmitter.send(userUpdatedEvent);

        return mapper.map(userEntity);
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

}

