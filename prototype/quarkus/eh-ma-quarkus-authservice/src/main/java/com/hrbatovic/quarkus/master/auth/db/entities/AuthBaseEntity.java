package com.hrbatovic.quarkus.master.auth.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

//TODO remove this base class
public class AuthBaseEntity extends PanacheMongoEntityBase {
    @BsonId
    public UUID id;

    public AuthBaseEntity() {
        this.id = UUID.randomUUID();
    }
}