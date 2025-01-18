package com.hrbatovic.springboot.master.user.api;

import com.hrbatovic.master.springboot.user.api.UsersApi;
import com.hrbatovic.master.springboot.user.model.*;
import com.hrbatovic.springboot.master.user.ClaimUtils;
import com.hrbatovic.springboot.master.user.db.entities.UserEntity;
import com.hrbatovic.springboot.master.user.db.repositories.UserRepository;
import com.hrbatovic.springboot.master.user.exceptions.EhMaException;
import com.hrbatovic.springboot.master.user.mapper.MapUtil;
import com.hrbatovic.springboot.master.user.messaging.model.out.UserDeletedEvent;
import com.hrbatovic.springboot.master.user.messaging.model.out.UserUpdatedEvent;
import com.hrbatovic.springboot.master.user.messaging.producers.UserDeletedEventProducer;
import com.hrbatovic.springboot.master.user.messaging.producers.UserUpdatedEventProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class UserApiService implements UsersApi {

    @Autowired
    MapUtil mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClaimUtils claimUtils;

    @Autowired
    UserUpdatedEventProducer userUpdatedEventProducer;

    @Autowired
    UserDeletedEventProducer userDeletedEventProducer;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public UserProfileResponse getUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if(userEntity == null){
            throw new EhMaException(404,"User not found");
        }

        if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN") && !id.equals(claimUtils.getUUIDClaim("sub"))){
            throw new EhMaException(400, "You are not allowed to view other user's account.");
        }

        return mapper.map(userEntity);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserListResponse listUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {

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
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public UserProfileResponse updateUser(UUID id, UpdateUserProfileRequest updateUserProfileRequest) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if(userEntity == null){
            throw new EhMaException(404, "User not found");
        }

        if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN")){

            if(!id.equals(claimUtils.getUUIDClaim("sub"))){
                throw new EhMaException(400, "You are not allowed to edit other user's data.");
            }

            if(StringUtils.isNotEmpty(updateUserProfileRequest.getRole())){
                throw new EhMaException(400, "You are not allowed change your role.");
            }

            mapper.updateNotAdmin(userEntity, updateUserProfileRequest);
        }else{
            mapper.update(userEntity, updateUserProfileRequest);
        }

        userRepository.save(userEntity);

        UserUpdatedEvent userUpdatedEvent = buildUserUpdatedEvent(userEntity);

        userUpdatedEventProducer.send(userUpdatedEvent);
        return mapper.map(userEntity);
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public DeleteUserResponse deleteUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if(userEntity == null){
            throw new EhMaException(404, "User not found.");
        }

        if(claimUtils.getRoles().contains("ROLE_CUSTOMER") && !claimUtils.getRoles().contains("ROLE_ADMIN") && !id.equals(claimUtils.getUUIDClaim("sub"))){
            throw new EhMaException(400, "You are not allowed to delete other user's account.");
        }

        userRepository.delete(userEntity);
        userDeletedEventProducer.send(buildUserDeletedEvent(userEntity));
        return new DeleteUserResponse().message("User deleted successfully");
    }

    private UserDeletedEvent buildUserDeletedEvent(UserEntity userEntity) {
        UserDeletedEvent userUpdatedEvent = new UserDeletedEvent();
        userUpdatedEvent.setId(userEntity.getId());
        userUpdatedEvent.setRequestCorrelationId(UUID.randomUUID());
        userUpdatedEvent.setSessionId(claimUtils.getUUIDClaim("sid"));
        userUpdatedEvent.setUserId(claimUtils.getUUIDClaim("sub"));
        userUpdatedEvent.setTimestamp(LocalDateTime.now());
        userUpdatedEvent.setUserEmail(claimUtils.getStringClaim("email"));
        return userUpdatedEvent;
    }


    private UserUpdatedEvent buildUserUpdatedEvent(UserEntity userEntity) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent().setUserPayload(mapper.mapFromEntity(userEntity));
        userUpdatedEvent.getUserPayload().setId(userEntity.getId());
        userUpdatedEvent.setRequestCorrelationId(UUID.randomUUID());
        userUpdatedEvent.setSessionId(claimUtils.getUUIDClaim("sid"));
        userUpdatedEvent.setUserId(claimUtils.getUUIDClaim("sub"));
        userUpdatedEvent.setTimestamp(LocalDateTime.now());
        userUpdatedEvent.setUserEmail(claimUtils.getStringClaim("email"));
        return userUpdatedEvent;
    }

}
