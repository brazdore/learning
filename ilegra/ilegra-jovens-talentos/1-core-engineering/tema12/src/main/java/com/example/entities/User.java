package com.example.entities;

import java.util.Date;
import java.util.Objects;

public class User {

    private String name;
    private Date birthDate;
    private Address address;

    public User() {
    }

    public User(String name, Date birthDate, Address address) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && birthDate.equals(user.birthDate) && address.equals(user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, address);
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", address=" + address +
                '}';
    }
}
