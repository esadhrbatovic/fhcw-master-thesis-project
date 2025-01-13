package com.hrbatovic.micronaut.master.cart.db.repositories;


import com.hrbatovic.micronaut.master.cart.db.entities.CartEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface CartRepository extends PageableRepository<CartEntity, UUID> {

    @MongoFindQuery("{'userId':  :userId}")
    Optional<CartEntity> findByUserId(UUID userId);

}
