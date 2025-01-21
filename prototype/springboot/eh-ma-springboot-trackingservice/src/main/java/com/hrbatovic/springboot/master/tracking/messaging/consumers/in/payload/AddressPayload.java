package com.hrbatovic.springboot.master.tracking.messaging.consumers.in.payload;

import java.io.Serializable;

public class AddressPayload implements Serializable {

    public AddressPayload() {
    }

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressPayload{");
        sb.append("street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
