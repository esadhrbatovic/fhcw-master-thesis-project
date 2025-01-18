package com.hrbatovic.springboot.master.cart.db.repositories;

import com.hrbatovic.springboot.master.cart.db.entities.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, UUID> {

    @Query(value= "{ '_id': { '$in': ?0 } }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<ProductEntity> findByProductIds(List<UUID> productIds);
}
