package com.example.entities;

import java.util.Objects;

public class Address {

    private String street;
    private int streetNumber;
    private String city;
    private String state;

    public Address() {
    }

    public Address(String street, int streetNumber, String city, String state) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && street.equals(address.street) && city.equals(address.city) && state.equals(address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, streetNumber, city, state);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
