package com.hrbatovic.micronaut.master.product.db.repositories;

import com.hrbatovic.micronaut.master.product.db.entities.CategoryEntity;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface CategoryRepository extends PageableRepository<CategoryEntity, UUID> {

    @MongoFindQuery("{'name':  :name}")
    Optional<CategoryEntity> findByName(String name);

    @MongoFindQuery("{ '_id': { '$in': :categoryIds } }")
    List<CategoryEntity> findByIds(List<UUID> categoryIds);

    @MongoFindQuery("{'name': { '$regex': :nameRegex, '$options': 'i' }}")
    Optional<CategoryEntity> findByNameRegex(String nameRegex);

    @MongoFindQuery("{ $and: :filter }")
    List<CategoryEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @MongoFindQuery("{}")
    List<CategoryEntity> findAll(List<Document> filter, Pageable pageable);

    default List<CategoryEntity> queryCategories(String search, Integer page, Integer limit) {
        List<Document> conditions = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String regexPattern = "^.*" + Pattern.quote(search) + ".*$";
            conditions.add(new Document("name", new Document("$regex", regexPattern).append("$options", "i")));
        }

        Pageable pageable = Pageable.from(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10
        );

        if (!conditions.isEmpty()) {
            return findByCustomFilter(conditions, pageable);
        }
        return findAll(new ArrayList<>(), pageable);
    }
}
