package com.hrbatovic.springboot.master.cart.messaging.consumers.model.in.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductPayload implements Serializable {
    private UUID id;

    private String title;

    private BigDecimal price;

    private String currency;

    private boolean licenseAvailable;

    private boolean deleted;

    public ProductPayload() {
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
}
