package com.hrbatovic.springboot.master.payment.db.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "payment-methods")
public class PaymentMethodEntity {

    @Id
    private UUID id;

    private String selector;

    private String name;

    private String description;

    private Boolean active;

    private String iconUrl;

    private String paymentGatewayUrl;

    private String merchantId;

    private String merchantSecret;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PaymentMethodEntity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPaymentGatewayUrl() {
        return paymentGatewayUrl;
    }

    public void setPaymentGatewayUrl(String paymentGatewayUrl) {
        this.paymentGatewayUrl = paymentGatewayUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantSecret() {
        return merchantSecret;
    }

    public void setMerchantSecret(String merchantSecret) {
        this.merchantSecret = merchantSecret;
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
        return "PaymentMethodEntity{" +
                "id=" + id +
                ", selector='" + selector + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", iconUrl='" + iconUrl + '\'' +
                ", paymentGatewayUrl='" + paymentGatewayUrl + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", merchantSecret='" + merchantSecret + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
