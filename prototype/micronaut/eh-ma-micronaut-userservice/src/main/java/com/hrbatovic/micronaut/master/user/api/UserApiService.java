package com.hrbatovic.micronaut.master.user.api;

import com.hrbatovic.micronaut.master.user.JwtUtil;
import com.hrbatovic.micronaut.master.user.db.entities.UserEntity;
import com.hrbatovic.micronaut.master.user.db.repositories.UserRepository;
import com.hrbatovic.micronaut.master.user.mapper.MapUtil;
import com.hrbatovic.micronaut.master.user.messaging.model.out.UserUpdatedEvent;
import com.hrbatovic.micronaut.master.user.messaging.producers.UserDeletedProducer;
import com.hrbatovic.micronaut.master.user.messaging.producers.UserUpdatedProducer;
import com.hrbatovic.micronaut.master.user.model.*;
import io.micronaut.http.annotation.Controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
@Controller
public class UserApiService implements UserApi {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    UserUpdatedProducer userUpdatedProducer;

    @Inject
    UserDeletedProducer userDeletedProducer;

    @Inject
    MapUtil mapper;

    @Override
    @RolesAllowed({"admin", "customer"})
    public UserProfileResponse getUser(UUID id) {


        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if(userEntity == null){
            throw new RuntimeException("User not found");
        }

        if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin") && !id.equals(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))){
            throw new RuntimeException("You are not allowed to view other user's account.");
        }

        return mapper.map(userEntity);
    }

    @Override
    @RolesAllowed({"admin"})
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, ListUsersSortParameter sort) {

        String sortString = sort != null ? sort.toString() : null;

        List<UserEntity> userEntityList = userRepository.queryUsers(page, limit, search, createdAfter, createdBefore, sortString);
        long totalItems = userEntityList.size();
        int totalPages = (int) Math.ceil((double) totalItems / (limit != null ? limit : 10));

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

        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if(userEntity == null){
            throw new RuntimeException("User not found");
        }

        if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin")){

            if(!id.equals(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))){
                throw new RuntimeException("You are not allowed to edit other user's data.");
            }

            if(StringUtils.isNotEmpty(updateUserProfileRequest.getRole())){
                throw new RuntimeException("You are not allowed change your role.");
            }

            mapper.updateNotAdmin(userEntity, updateUserProfileRequest);
        }else{
            mapper.update(userEntity, updateUserProfileRequest);
        }

        userRepository.update(userEntity);

        UserUpdatedEvent userUpdatedEvent = buildUserUpdatedEvent(userEntity);

        userUpdatedProducer.send(userUpdatedEvent);
        return mapper.map(userEntity);
    }



    @Override
    @RolesAllowed({"admin", "customer"})
    public DeleteUserResponse deleteUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null){
            throw new RuntimeException("User not found.");
        }

        if(jwtUtil.getRoles().contains("customer") && !jwtUtil.getRoles().contains("admin") && !id.equals(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")))){
            throw new RuntimeException("You are not allowed to delete other user's account.");
        }

        userRepository.delete(userEntity);
        userDeletedProducer.send(id);
        return new DeleteUserResponse().message("User deleted successfully");
    }

    private UserUpdatedEvent buildUserUpdatedEvent(UserEntity userEntity) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent().setUserPayload(mapper.mapFromEntity(userEntity));
        userUpdatedEvent.getUserPayload().setId(userEntity.getId());
        userUpdatedEvent.setRequestCorrelationId(UUID.randomUUID());
        userUpdatedEvent.setSessionId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sid")));
        userUpdatedEvent.setUserId(UUID.fromString(jwtUtil.getClaimFromSecurityContext("sub")));
        userUpdatedEvent.setTimestamp(LocalDateTime.now());
        userUpdatedEvent.setUserEmail(jwtUtil.getClaimFromSecurityContext("email"));
        return userUpdatedEvent;
    }

}
