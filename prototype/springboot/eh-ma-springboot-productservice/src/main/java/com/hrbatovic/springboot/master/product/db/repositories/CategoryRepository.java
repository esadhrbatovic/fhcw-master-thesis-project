package com.hrbatovic.springboot.master.product.db.repositories;

import com.hrbatovic.springboot.master.product.db.entities.CategoryEntity;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, UUID> {


    @Query(value = "{'name':  ?0}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    Optional<CategoryEntity> findByName(String name);

    @Query(value = "{ '_id': { '$in': ?0 } }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<CategoryEntity> findByIds(List<UUID> categoryIds);

    @Query(value = "{'name': { '$regex': ?0, '$options': 'i' }}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    Optional<CategoryEntity> findByNameRegex(String nameRegex);

    @Query(value = "{ $and: ?0 }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<CategoryEntity> findByCustomFilter(List<Document> filter, Pageable pageable);

    @Query(value= "{}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<CategoryEntity> findAll(List<Document> filter, Pageable pageable);

    default List<CategoryEntity> queryCategories(String search, Integer page, Integer limit) {
        List<Document> conditions = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String regexPattern = "^.*" + Pattern.quote(search) + ".*$";
            conditions.add(new Document("name", new Document("$regex", regexPattern).append("$options", "i")));
        }

        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10
        );

        if (!conditions.isEmpty()) {
            return findByCustomFilter(conditions, pageable);
        }
        return findAll(new ArrayList<>(), pageable);
    }

}
