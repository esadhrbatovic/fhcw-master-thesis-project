package com.hrbatovic.springboot.master.cart.db.repositories;

import com.hrbatovic.springboot.master.cart.db.entities.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends MongoRepository<CartEntity, UUID> {

    @Query(value = "{'userId':  ?0}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    Optional<CartEntity> findByUserId(UUID userId);

}
