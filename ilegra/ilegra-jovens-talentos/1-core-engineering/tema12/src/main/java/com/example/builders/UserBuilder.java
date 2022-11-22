package com.example.builders;

import com.example.entities.Address;
import com.example.entities.User;

import java.util.Date;

public class UserBuilder {

    private String name;
    private Date birthDate;
    private String streetName;
    private int streetNumber;
    private String city;
    private String state;

    private UserBuilder() {
    }

    public static IUsername name(String name) {
        return new UserBuilder.Builder(name);
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getStreetName() {
        return streetName;
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

    public interface IUsername {
        IBirthdate birthDate(Date birthDate);
    }

    public interface IBirthdate {
        IStreetName streetName(String streetName);
    }

    public interface IStreetName {
        IStreetNumber streetNumber(int streetNumber);
    }

    public interface IStreetNumber {
        ICity city(String city);
    }

    public interface ICity {
        IState state(String state);
    }

    public interface IState {
        User build();
    }

    private static class Builder implements IUsername, IBirthdate, IStreetName, IStreetNumber, ICity, IState {
        private final UserBuilder instance = new UserBuilder();

        public Builder(String name) {
            instance.name = name;
        }

        private User toUser() {
            return new User(instance.getName(), instance.getBirthDate(),
                    new Address(instance.getStreetName(), instance.getStreetNumber(),
                            instance.getCity(), instance.getState()));
        }

        @Override
        public IBirthdate birthDate(Date birthDate) {
            instance.birthDate = birthDate;
            return this;
        }

        @Override
        public IStreetName streetName(String streetName) {
            instance.streetName = streetName;
            return this;
        }

        @Override
        public IStreetNumber streetNumber(int streetNumber) {
            instance.streetNumber = streetNumber;
            return this;
        }

        @Override
        public ICity city(String city) {
            instance.city = city;
            return this;
        }

        @Override
        public IState state(String state) {
            instance.state = state;
            return this;
        }

        @Override
        public User build() {
            return toUser();
        }
    }
}
