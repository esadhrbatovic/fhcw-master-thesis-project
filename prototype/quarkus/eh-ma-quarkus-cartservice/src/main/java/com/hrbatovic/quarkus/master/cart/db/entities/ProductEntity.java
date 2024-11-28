package com.hrbatovic.quarkus.master.cart.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.math.BigDecimal;
import java.util.UUID;

@MongoEntity(collection = "products")
public class ProductEntity extends PanacheMongoEntityBase {

    @BsonId
    private UUID id;

    private String title;

    private BigDecimal price;

    private String currency;

    private boolean licenseAvailable;

    private boolean deleted;

    public ProductEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isLicenseAvailable() {
        return licenseAvailable;
    }

    public void setLicenseAvailable(boolean licenseAvailable) {
        this.licenseAvailable = licenseAvailable;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", licenseAvailable=" + licenseAvailable +
                ", deleted=" + deleted +
                '}';
    }
}
