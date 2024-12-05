package com.hrbatovic.quarkus.master.auth.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@MongoEntity(collection = "registrations")
public class RegistrationEntity extends PanacheMongoEntityBase {
    @BsonId
    public UUID id;

    private UserEntity userEntity;
    private CredentialsEntity credentialsEntity;

    public RegistrationEntity() {
        this.id = UUID.randomUUID();
    }

    public static RegistrationEntity findByUserid(UUID userId) {
        PanacheQuery<RegistrationEntity> result = RegistrationEntity.find("{'userEntity._id': ?1}", userId);

        return result.firstResult();
    }

    public static RegistrationEntity findByEmail(String email) {
        String query = String.format("{'credentialsEntity.email': '%s'}", email);
        PanacheQuery<RegistrationEntity> result = RegistrationEntity.find(query);
        return result.firstResult();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CredentialsEntity getCredentialsEntity() {
        return credentialsEntity;
    }

    public void setCredentialsEntity(CredentialsEntity credentialsEntity) {
        this.credentialsEntity = credentialsEntity;
    }
}