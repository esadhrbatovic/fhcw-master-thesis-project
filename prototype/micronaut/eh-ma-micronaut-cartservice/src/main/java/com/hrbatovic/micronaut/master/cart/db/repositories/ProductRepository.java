package com.hrbatovic.micronaut.master.cart.db.repositories;

import com.hrbatovic.micronaut.master.cart.db.entities.ProductEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {

    @MongoFindQuery("{ '_id': { '$in': :productIds } }")
    List<ProductEntity> findByProductIds(List<UUID> productIds);
}
