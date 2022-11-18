package org.example.patterns.creational;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Arrays;

public class Prototype {

    public static void main(String[] args) {
        Person pomu = new Person(new String[]{"Pomu", "Rainpuff"}, new Address("Pomu St.", 2));

        Person finana = pomu.clone();
        finana.names = new String[]{"Finana Ryugu"};
        finana.address.number = 3;

        System.out.println(pomu);
        System.out.println(finana);

        System.out.println("---");

        Person elira = new Person(new String[]{"Elira", "Pendora"}, new Address("Elira Avenue", 190));
        Person eliraCopy = new Person(elira);

        System.out.println(elira);
        System.out.println(eliraCopy);
        System.out.println(elira == eliraCopy);
        System.out.println(elira.equals(eliraCopy));
        System.out.println(elira.address == eliraCopy.address);

        System.out.println("---");

        Person serializedElira = SerializationUtils.roundtrip(elira); // Copy Through Serialization. Easiest way when having multiple complex dependencies. Must implement Serializable.
        System.out.println(serializedElira);
        System.out.println(serializedElira == elira);
        System.out.println(serializedElira.equals(elira));
    }

    static class Address implements Cloneable, Serializable {
        protected String streetName;
        protected Integer number;

        public Address(String streetName, Integer number) {
            this.streetName = streetName;
            this.number = number;
        }

        public Address(Address other) { // Copy Constructor.
            this(other.streetName, other.number);
        }

        @Override
        public Address clone() { // Cloneable override implementation. It is better to implement a deep copy clone() method instead of relying on default.
            return new Address(streetName, number);
        }

        @Override
        public String toString() {
            return "Address{" +
                    "streetName='" + streetName + '\'' +
                    ", number=" + number +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return streetName.equals(address.streetName) && number.equals(address.number);
        }
    }

    static class Person implements Cloneable, Serializable {
        protected String[] names;
        protected Address address;

        public Person(String[] names, Address address) {
            this.names = names;
            this.address = address;
        }

        public Person(Person other) {
            this.names = other.names;
            this.address = new Address(other.address);
        }

        @Override
        public Person clone() {
            return new Person(Arrays.copyOf(names, names.length), address.clone());
        }

        @Override
        public String toString() {
            return "Person{" +
                    "names=" + Arrays.toString(names) +
                    ", address=" + address +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Arrays.equals(names, person.names) && address.equals(person.address);
        }
    }

    // Exercise

    static class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(Point otherPoint) { // Copy Constructor.
            this.x = otherPoint.x;
            this.y = otherPoint.y;
        }
    }

    static class Line {
        public Point start, end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public Line deepCopy() {
            return new Line(new Point(start), new Point(end));
        }
    }
}
