package com.example.entities;

import java.util.Objects;

public class User {

    private long userID;
    private String name;
    private int allRentedAmount;
    private int currentHolding;

    public User(long userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public User() {
    }

    public long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public int getAllRentedAmount() {
        return allRentedAmount;
    }

    public void increaseAllRentedAmount(int i) {
        allRentedAmount += i;
    }

    public int getCurrentHolding() {
        return currentHolding;
    }

    public void increaseCurrentHolding(int i) {
        currentHolding += i;
    }

    public void decreaseCurrentHolding(int i) {
        currentHolding -= i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", rentedAmount=" + allRentedAmount +
                '}';
    }
}
