package com.hrbatovic.quarkus.master.user.api;

import com.hrbatovic.master.quarkus.user.api.UsersApi;
import com.hrbatovic.master.quarkus.user.model.*;
import com.hrbatovic.quarkus.master.user.db.entities.UserEntity;
import com.hrbatovic.quarkus.master.user.exceptions.EhMaException;
import com.hrbatovic.quarkus.master.user.mapper.MapUtil;
import com.hrbatovic.quarkus.master.user.messaging.model.out.UserUpdatedEvent;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.*;

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

    @Inject
    @Claim(standard = Claims.groups)
    Set<String> groupsClaim;

    @Inject
    @Claim(standard = Claims.sub)
    String userSubClaim;

    @Inject
    @Claim("sid")
    String sessionIdClaim;

    @Inject
    @Claim(standard = Claims.email)
    String emailClaim;

    @Override
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse getUser(UUID id) {
        ApiInputValidator.validateUserId(id);
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null){
            throw new EhMaException(404, "User not found");
        }

        if(groupsClaim.contains("customer") && !groupsClaim.contains("admin") && !id.equals(UUID.fromString(userSubClaim))){
            throw new EhMaException(400, "You are not allowed to view other user's account.");
        }

        return mapper.map(userEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

        PanacheQuery<UserEntity> query = UserEntity.queryUsers(page, limit, search, createdAfter, createdBefore, sort);

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
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse updateUser(UUID id, UpdateUserProfileRequest updateUserProfileRequest) {
        ApiInputValidator.validateUpdateUser(updateUserProfileRequest);
        if(groupsClaim.contains("admin") && StringUtils.isEmpty(updateUserProfileRequest.getRole())){
            throw new EhMaException(400, "You need to provde the user's role.");
        }

        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null ){
            throw new EhMaException(404, "User not found");
        }
        if(groupsClaim.contains("customer") && !groupsClaim.contains("admin")){
            if(!id.equals(UUID.fromString(userSubClaim))){
                throw new EhMaException(400, "You are not allowed to edit other user's data.");
            }

            if(StringUtils.isNotEmpty(updateUserProfileRequest.getRole())){
                throw new EhMaException(400, "You are not allowed change your role.");
            }
            mapper.updateNotAdmin(userEntity, updateUserProfileRequest);
        }else{
            mapper.update(userEntity, updateUserProfileRequest);
        }

        userEntity.persistOrUpdate();

        UserUpdatedEvent userUpdatedEvent = buildUserUpdatedEvent(userEntity);

        userUpdatedEmitter.send(userUpdatedEvent);

        return mapper.map(userEntity);
    }

    @Override
    @RolesAllowed({"admin", "customer"})
    public DeleteUserResponse deleteUser(UUID id) {
        ApiInputValidator.validateUserId(id);
        UserEntity userEntity = UserEntity.findById(id);
        if(userEntity == null){
            throw new EhMaException(404, "User not found");
        }

        if(groupsClaim.contains("customer") && !groupsClaim.contains("admin") && !id.equals(UUID.fromString(userSubClaim))){
            throw new EhMaException(400, "You are not allowed to delete other user's account.");
        }

        userEntity.delete();
        userDeletedEmitter.send(id);
        return new DeleteUserResponse().message("User deleted successfully");
    }

    private UserUpdatedEvent buildUserUpdatedEvent(UserEntity userEntity) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent().setUserPayload(mapper.mapFromEntity(userEntity));
        userUpdatedEvent.getUserPayload().setId(userEntity.getId());
        userUpdatedEvent.setRequestCorrelationId(UUID.randomUUID());
        userUpdatedEvent.setSessionId(UUID.fromString(sessionIdClaim));
        userUpdatedEvent.setUserId(UUID.fromString(userSubClaim));
        userUpdatedEvent.setTimestamp(LocalDateTime.now());
        userUpdatedEvent.setUserEmail(emailClaim);
        return userUpdatedEvent;
    }

}

