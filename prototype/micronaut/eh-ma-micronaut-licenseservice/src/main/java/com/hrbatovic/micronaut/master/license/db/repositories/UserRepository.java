package com.hrbatovic.micronaut.master.license.db.repositories;

import com.hrbatovic.micronaut.master.license.db.entities.UserEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
}
