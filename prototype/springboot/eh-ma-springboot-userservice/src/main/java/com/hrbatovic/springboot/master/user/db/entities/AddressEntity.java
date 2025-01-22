package com.hrbatovic.springboot.master.user.db.entities;

import com.hrbatovic.springboot.master.user.exceptions.EhMaExceptionHandler;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AddressEntity {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public AddressEntity() {

    }

    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public AddressEntity setState(String state) {
        this.state = state;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressEntity setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressEntity setCountry(String country) {
        this.country = country;
        return this;
    }

}
