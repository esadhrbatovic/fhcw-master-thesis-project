package com.hrbatovic.quarkus.master.order.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@MongoEntity(collection = "products")
public class ProductEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                '}';
    }
}
