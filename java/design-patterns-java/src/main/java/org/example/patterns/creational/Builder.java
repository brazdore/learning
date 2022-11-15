package org.example.patterns.creational;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Builder {

    public static void main(String[] args) {
        // stringBuilder();

        // HtmlBuilder builder = new HtmlBuilder("ul");

        // builder.addChild("li", "I'm");
        // builder.addChild("li", "Pomu");

        // builder.addChildFluent("li", "I'm")
        //     .addChildFluent("li", "Pomu");
        // System.out.println(builder);

        // PersonBuilder pb = new PersonBuilder();
        // Person rosemi = pb
        //        .withName("Rosemi Lovelock")
        //        .worksAs("VTuber") // Does not work because worksAs() is not defined inside PersonBuilder and we are building with a fluent builder.
        //        .build();

        // BetterPersonBuilder bpb = new BetterPersonBuilder<BetterEmployeeBuilder>(); // OK!
        // BetterPersonBuilder bpb = new BetterPersonBuilder<Person>(); // Error! Type Person should extend BetterPersonBuilder.

        // BetterEmployeeBuilder beb = new BetterEmployeeBuilder();
        // Person rosemi = beb.withName("Rosemi Lovelock")
        //         .worksAs("VTuber").build();

        // System.out.println(rosemi);

        VTuberBuilder vb = new VTuberBuilder();
        VTuber finana = vb
                .character()
                .name("Finana Ryugu")
                .race("Mermaid")
                .age("[REDACTED]")

                .company()
                .at("NIJISANJI")
                .branch("EN")
                .generation("LazuLight")

                .build();

        System.out.println(finana);

        // CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
        // System.out.println(cb);
    }

    static void stringBuilder() { // Native builder for Strings.
        String[] arr = {"I'm", "Pomu"};
        StringBuilder sb = new StringBuilder();

        sb.append("<ul>\n");
        for (String s : arr) {
            sb.append(String.format("  <li>%s<li>\n", s));
        }
        sb.append("<ul>");
        System.out.println(sb);
    }

    static class HtmlElement {
        private final int indentSize = 2;
        private final String newLine = System.lineSeparator();
        public String name, text;
        public ArrayList<HtmlElement> elements = new ArrayList<>();

        public HtmlElement() {
        }

        public HtmlElement(String name, String text) {
            this.name = name;
            this.text = text;
        }

        private String toStringImpl(int indent) {
            StringBuilder sb = new StringBuilder();
            String i = String.join("", Collections.nCopies(indent * indentSize, " "));
            sb.append(String.format("%s<%s>%s", i, name, newLine));
            if (text != null && !text.isEmpty()) {
                sb.append(String.join("", Collections.nCopies(indentSize * (indent + 1), " ")))
                        .append(text)
                        .append(newLine);
            }

            for (HtmlElement e : elements) {
                sb.append(e.toStringImpl(indent + 1));
            }

            sb.append(String.format("%s</%s>%s", i, name, newLine));
            return sb.toString();
        }

        @Override
        public String toString() {
            return toStringImpl(0);
        }
    }

    static class HtmlBuilder {
        private String rootName;
        private HtmlElement root = new HtmlElement();

        public HtmlBuilder(String rootName) {
            this.rootName = rootName;
            root.name = rootName;
        }

        public void addChild(String childName, String childText) { // Not fluent.
            HtmlElement e = new HtmlElement(childName, childText);
            root.elements.add(e);
        }

        public HtmlBuilder addChildFluent(String childName, String childText) { // Fluent, as it returns itself and allow method chaining.
            HtmlElement e = new HtmlElement(childName, childText);
            root.elements.add(e);
            return this;
        }

        public void clear() {
            root = new HtmlElement();
            root.name = rootName;
        }

        @Override
        public String toString() {
            return root.toString();
        }
    }

    // Fluent Builder with Recursive Generics
    static class Person {
        private String name;
        private String position;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", position='" + position + '\'' +
                    '}';
        }
    }

    static class PersonBuilder {
        protected Person person = new Person();

        public PersonBuilder withName(String name) {
            person.name = name;
            return this;
        }

        public Person build() {
            return person;
        }
    }

    static class EmployeeBuilder extends PersonBuilder {
        public EmployeeBuilder worksAs(String position) {
            person.position = position;
            return this;
        }
    }

    static class BetterPersonBuilder<T extends BetterPersonBuilder<T>> { // Recursive generics to maintain builder fluency with inheritance.
        protected Person person = new Person();

        public T withName(String name) {
            person.name = name;
            return self();
        }

        public Person build() {
            return person;
        }

        public T self() { // Casting should be OK. It should be impossible to create an instance of BetterPersonBuilder<T> when type T does not extend BetterPersonBuilder.
            return (T) this;
        }
    }

    static class BetterEmployeeBuilder extends BetterPersonBuilder<BetterEmployeeBuilder> {
        public BetterEmployeeBuilder worksAs(String position) {
            person.position = position;
            return self();
        }

        @Override
        public BetterEmployeeBuilder self() {
            return this;
        }
    }

    // Faceted Builder
    static class VTuber {
        // Character
        private String name, race, age;
        // Company/Structure
        private String company, branch, generation;

        @Override
        public String toString() {
            return "VTuber{" +
                    "name='" + name + '\'' +
                    ", race='" + race + '\'' +
                    ", age='" + age + '\'' +
                    ", company='" + company + '\'' +
                    ", branch='" + branch + '\'' +
                    ", generation='" + generation + '\'' +
                    '}';
        }
    }

    static class VTuberBuilder {
        protected VTuber vtuber = new VTuber();

        public VTuberCharacterBuilder character() {
            return new VTuberCharacterBuilder(vtuber);
        }

        public VTuberCompanyBuilder company() {
            return new VTuberCompanyBuilder(vtuber);
        }

        public VTuber build() {
            return vtuber;
        }
    }

    static class VTuberCharacterBuilder extends VTuberBuilder {
        public VTuberCharacterBuilder(VTuber vtuber) {
            this.vtuber = vtuber;
        }

        public VTuberCharacterBuilder name(String name) {
            vtuber.name = name;
            return this;
        }

        public VTuberCharacterBuilder race(String race) {
            vtuber.race = race;
            return this;
        }

        public VTuberCharacterBuilder age(String age) {
            vtuber.age = age;
            return this;
        }
    }

    static class VTuberCompanyBuilder extends VTuberBuilder {
        public VTuberCompanyBuilder(VTuber vtuber) {
            this.vtuber = vtuber;
        }

        public VTuberCompanyBuilder at(String company) {
            vtuber.company = company;
            return this;
        }

        public VTuberCompanyBuilder branch(String branch) {
            vtuber.branch = branch;
            return this;
        }

        public VTuberCompanyBuilder generation(String generation) {
            vtuber.generation = generation;
            return this;
        }
    }

    // Exercise
    static class Field {
        protected String type, name;

        public Field(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    static class Class {
        protected String className;
        protected List<Field> fields = new ArrayList<>();

        public Class() {
        }

        public Class(String className, List<Field> fields) {
            this.className = className;
            this.fields = fields;
        }
    }

    static class CodeBuilder {
        protected Class aClass = new Class();

        public CodeBuilder(String className) {
            aClass.className = className;
        }

        public CodeBuilder addField(String name, String type) {
            aClass.fields.add(new Field(type, name));
            return this;
        }

        private String indent() {
            int baseIndentation = 2;
            return String.join("", Collections.nCopies(baseIndentation, " "));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("public class ")
                    .append(aClass.className)
                    .append(System.lineSeparator())
                    .append("{")
                    .append(System.lineSeparator());

            aClass.fields.forEach(f ->
                    sb.append(indent())
                            .append("public ")
                            .append(f.type)
                            .append(" ")
                            .append(f.name)
                            .append(";")
                            .append(System.lineSeparator()));

            sb.append("}");
            return sb.toString();
        }
    }
}
