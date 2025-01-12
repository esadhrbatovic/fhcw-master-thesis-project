package com.hrbatovic.micronaut.master.auth.db.repositories;

import com.hrbatovic.micronaut.master.auth.db.entities.RegistrationEntity;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@MongoRepository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, UUID> {

    @MongoFindQuery("{'userEntity._id': :userId}")
    Optional<RegistrationEntity> findByUserEntityId(UUID userId);

    @MongoFindQuery("{'credentialsEntity.email': :email}")
    Optional<RegistrationEntity> findByCredentialsEntityEmail(String email);


    Optional<RegistrationEntity> findById(UUID id);
}