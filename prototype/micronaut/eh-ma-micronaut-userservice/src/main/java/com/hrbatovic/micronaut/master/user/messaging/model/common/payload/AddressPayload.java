package com.hrbatovic.micronaut.master.user.messaging.model.common.payload;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
public class AddressPayload implements Serializable {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public AddressPayload() {
    }

    public String getStreet() {
        return street;
    }

    public AddressPayload setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressPayload setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public AddressPayload setState(String state) {
        this.state = state;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressPayload setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressPayload setCountry(String country) {
        this.country = country;
        return this;
    }

}