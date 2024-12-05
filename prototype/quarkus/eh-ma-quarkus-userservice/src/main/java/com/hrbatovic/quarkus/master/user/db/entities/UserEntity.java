package com.hrbatovic.quarkus.master.user.db.entities;

import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.bson.codecs.pojo.annotations.BsonId;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@MongoEntity(collection = "users")
public class UserEntity extends PanacheMongoEntityBase  {
    @BsonId
    public UUID id;

    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private String phoneNumber;
    private AddressEntity address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserEntity(){
        this.id = UUID.randomUUID();
    }


    public static PanacheQuery<UserEntity> findUsers(Integer page, Integer limit, String search, LocalDateTime createdAfter, LocalDateTime createdBefore, String sort) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        List<String> conditions = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            String regexPattern = ".*" + Pattern.quote(search) + ".*";
            conditions.add("{ '$or': [ "
                    + "{ 'firstName': { '$regex': :searchPattern, '$options': 'i' } }, "
                    + "{ 'lastName': { '$regex': :searchPattern, '$options': 'i' } }, "
                    + "{ 'email': { '$regex': :searchPattern, '$options': 'i' } } "
                    + "] }");
            params.put("searchPattern", regexPattern);
        }

        if (createdAfter != null) {
            conditions.add("{ 'createdAt': { '$gte': :createdAfter } }");
            params.put("createdAfter", createdAfter);
        }

        if (createdBefore != null) {
            conditions.add("{ 'createdAt': { '$lte': :createdBefore } }");
            params.put("createdBefore", createdBefore);
        }

        if (!conditions.isEmpty()) {
            if (conditions.size() == 1) {
                queryBuilder.append(conditions.get(0));
            } else {
                queryBuilder.append("{ '$and': [ ");
                queryBuilder.append(String.join(", ", conditions));
                queryBuilder.append(" ] }");
            }
        } else {
            queryBuilder.append("{}");
        }

        Sort sortOrder = null;
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "nameAsc":
                    sortOrder = Sort.ascending("firstName");
                    break;
                case "nameDesc":
                    sortOrder = Sort.descending("firstName");
                    break;
                case "dateAsc":
                    sortOrder = Sort.ascending("createdAt");
                    break;
                case "dateDesc":
                    sortOrder = Sort.descending("createdAt");
                    break;
                default:
                    break;
            }
        }

        PanacheQuery<UserEntity> query;
        if (sortOrder != null) {
            query = find(queryBuilder.toString(), sortOrder, params);
        } else {
            query = find(queryBuilder.toString(), params);
        }

        Collation collation = Collation.builder()
                .locale("en")
                .collationStrength(CollationStrength.PRIMARY) // Case-insensitive
                .build();
        query = query.withCollation(collation);

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 10;
        }

        query.page(Page.of(page - 1, limit));

        return query;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", deliveryMail='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}