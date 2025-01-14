package com.hrbatovic.springboot.master.auth.db.repositories;

import com.hrbatovic.springboot.master.auth.db.entities.RegistrationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends MongoRepository<RegistrationEntity, UUID> {

    @Query("{ 'userEntity._id': ?0 }")
    Optional<RegistrationEntity> findByUserEntityId(UUID userId);

    @Query("{ 'credentialsEntity.email': ?0 }")
    Optional<RegistrationEntity> findByCredentialsEntityEmail(String email);

}