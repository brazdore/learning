package org.example.solid;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// A. High-level modules should not depend on low-level modules.
// Both should depend on abstractions.

// B. Abstractions should not depend on details.
// Details should depend on abstractions.

public class DIP { // Dependency Inversion Principle

    public static void main(String[] args) {
        Person parent = new Person("Nina");
        Person child1 = new Person("Enna");
        Person child2 = new Person("Millie");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships);
        System.out.println("---");
        new Research(relationships, "a");
    }

    enum Relationship {
        PARENT,
        CHILD,
        SIBLING
    }

    interface RelationshipBrowser {
        List<Person> findAllChildrenOf(String name);
    }

    static class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }
    }

    static class Relationships implements RelationshipBrowser { // Low-level module: related to data storage.
        private final List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

        @Override
        public List<Person> findAllChildrenOf(String name) {
            return relations.stream()
                    .filter(x -> Objects.equals(x.getValue0().name, name)
                            && x.getValue1() == Relationship.PARENT)
                    .map(Triplet::getValue2)
                    .collect(Collectors.toList());
        }

        public List<Triplet<Person, Relationship, Person>> getRelations() {
            return relations;
        }

        public void addParentAndChild(Person parent, Person child) {
            relations.add(new Triplet<>(parent, Relationship.PARENT, child));
            relations.add(new Triplet<>(child, Relationship.CHILD, parent));
        }
    }

    static class Research { // High-level module: related to business logic and more visible to user.
        public Research(Relationships relationships) { // Bad! Our high-level module Research depends on low-level module Relationships.
            List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations(); // It does not need to know Relationships implementation of data storage.
            relations.stream()
                    .filter(x -> x.getValue0().name.equals("Nina")
                            && x.getValue1() == Relationship.PARENT)
                    .forEach(ch -> System.out.println("Nina has a child called " + ch.getValue2().name));
        }

        public Research(RelationshipBrowser browser, String s) { // Good! It now depends on abstraction. [`s` is used only to make signatures different]
            List<Person> children = browser.findAllChildrenOf("Nina");
            for (Person child : children)
                System.out.println("Nina has a child called " + child.name);
        }
    }
}
