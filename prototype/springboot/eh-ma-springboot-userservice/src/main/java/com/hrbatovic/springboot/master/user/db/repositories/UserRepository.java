package com.hrbatovic.springboot.master.user.db.repositories;

import com.hrbatovic.springboot.master.user.db.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, UUID> {

    @Query(value = "{ '$and': [ { 'createdAt': { '$gte': ?0, '$lte': ?1 } }, { '$or': [ { 'firstName': { '$regex': ?2, '$options': 'i' } }, { 'lastName': { '$regex': ?3, '$options': 'i' } }, { 'email': { '$regex': ?4, '$options': 'i' } } ] } ]}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<UserEntity> findByCreatedAtBetweenAndSearch(
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            String firstNameRegex,
            String lastNameRegex,
            String emailRegex,
            Pageable pageable
    );

    @Query(value = "{ '$or': [ { 'firstName': { '$regex': ?0, '$options': 'i' } }, { 'lastName': { '$regex': ?1, '$options': 'i' } }, { 'email': { '$regex': ?2, '$options': 'i' } } ] }", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    List<UserEntity> findBySearch(
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
        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                limit != null ? limit : 10,
                resolveSort(sort)
        );

        if (createdAfter != null || createdBefore != null) {
            return findByCreatedAtBetweenAndSearch(
                    createdAfter != null ? createdAfter : LocalDateTime.MIN,
                    createdBefore != null ? createdBefore : LocalDateTime.MAX,
                    searchPattern,
                    searchPattern,
                    searchPattern,
                    pageable
            );
        } else {
            return findBySearch(
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
                return Sort.by(Sort.Order.asc("firstName"));
            case "nameDesc":
                return Sort.by(Sort.Order.desc("firstName"));
            case "dateAsc":
                return Sort.by(Sort.Order.asc("createdAt"));
            case "dateDesc":
                return Sort.by(Sort.Order.desc("createdAt"));
            default:
                return Sort.unsorted();
        }
    }
}
