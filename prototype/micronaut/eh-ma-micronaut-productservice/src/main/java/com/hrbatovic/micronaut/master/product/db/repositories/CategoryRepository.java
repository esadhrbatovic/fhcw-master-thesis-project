package com.hrbatovic.micronaut.master.product.db.repositories;

import com.hrbatovic.micronaut.master.product.db.entities.CategoryEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface CategoryRepository extends PageableRepository<CategoryEntity, UUID> {

    @MongoFindQuery("{'name':  :name}")
    Optional<CategoryEntity> findByName(String name);

    @MongoFindQuery("{ '_id': { '$in': :categoryIds } }")
    List<CategoryEntity> findByIds(List<UUID> categoryIds);

    @MongoFindQuery("{'name': { '$regex': :nameRegex, '$options': 'i' }}")
    Optional<CategoryEntity> findByNameRegex(String nameRegex);

}
