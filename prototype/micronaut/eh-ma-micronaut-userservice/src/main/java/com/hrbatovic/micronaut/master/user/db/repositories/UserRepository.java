package com.hrbatovic.micronaut.master.user.db.repositories;

import com.hrbatovic.micronaut.master.user.db.entities.UserEntity;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface UserRepository extends PageableRepository<UserEntity, UUID> {

    @MongoFindQuery("{ $or: [{ 'firstName': { '$regex': :firstNameRegex, '$options': 'i' } }, { 'lastName': { '$regex': :lastNameRegex, '$options': 'i' } }, { 'email': { '$regex': :emailRegex, '$options': 'i' } } ] }")
    List<UserEntity> findByCreatedAtBetweenAndFirstNameRegexAndLastNameRegexAndEmailRegex(
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            String firstNameRegex,
            String lastNameRegex,
            String emailRegex,
            Pageable pageable
    );

    @MongoFindQuery("{ $or: [{ 'firstName': { '$regex': :firstNameRegex, '$options': 'i' } }, { 'lastName': { '$regex': :lastNameRegex, '$options': 'i' } }, { 'email': { '$regex': :emailRegex, '$options': 'i' } } ] }")
    List<UserEntity> findByFirstNameRegexAndLastNameRegexAndEmailRegex(
            String firstNameRegex,
            String lastNameRegex,
            String emailRegex,
            Pageable pageable
    );

    default List<UserEntity> queryUsers(
            Integer page,
            Integer limit,
            String search,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            String sort
    ) {
        String searchPattern = search != null ? ".*" + search + ".*" : ".*";
        Pageable pageable = Pageable.from(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                resolveSort(sort)
        );


        if (createdAfter != null || createdBefore != null) {
            return findByCreatedAtBetweenAndFirstNameRegexAndLastNameRegexAndEmailRegex(
                    createdAfter != null ? createdAfter : LocalDateTime.MIN,
                    createdBefore != null ? createdBefore : LocalDateTime.MAX,
                    searchPattern,
                    searchPattern,
                    searchPattern,
                    pageable
            );
        } else {
            return findByFirstNameRegexAndLastNameRegexAndEmailRegex(
                    searchPattern,
                    searchPattern,
                    searchPattern,
                    pageable
            );
        }
    }

    private Sort resolveSort(String sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        switch (sort) {
            case "nameAsc":
                return Sort.of(Sort.Order.asc("firstName"));
            case "nameDesc":
                return Sort.of(Sort.Order.desc("firstName"));
            case "dateAsc":
                return Sort.of(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.of(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }
}
