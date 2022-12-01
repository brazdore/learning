package org.example.patterns.structural;

import org.example.utils.Line;

import java.util.function.Supplier;

public class Decorator {
    public static void main(String[] args) {
        MagicString pomu = new MagicString("Pomu");
        System.out.println(pomu.countVowels());
        System.out.println(pomu);

        Line.split();

        Circle circle = new Circle(2F);
        System.out.println(circle.getInfo());

        ColoredShape blueSquare = new ColoredShape(new Square(2F), "Blue");
        System.out.println(blueSquare.getInfo());

        TransparentShape transparentCircle = new TransparentShape(new Circle(4F), 50);
        System.out.println(transparentCircle.getInfo());

        TransparentShape tranparentColoredSquare = new TransparentShape(new ColoredShape(new Square(2F), "Purple"), 50);
        System.out.println(tranparentColoredSquare.getInfo());

        Line.split();

        StaticColoredShape<Square> yellowSquare = new StaticColoredShape<>(() -> new Square(2F), "Yellow");
        System.out.println(yellowSquare.getInfo());

        StaticTransparentShape<StaticColoredShape<Circle>> greyTranparentCircle = new StaticTransparentShape<>(() -> new StaticColoredShape<>(() -> new Circle(4F), "Grey"), 50);
        System.out.println(greyTranparentCircle.getInfo());

        Line.split();

        MyStringBuilder myStringBuilder = new MyStringBuilder("I'm Pomu.");
        MyStringBuilder concat = myStringBuilder.appendLine(" I'm Pomu too.").concat("We are all Pomu!");
        System.out.println(concat);
    }

    // Dynamic Decorator
    interface Shape {
        String getInfo();
    }

    static class MagicString {
        private static final String VOWELS = "AEIOUaeiou";
        private final String string;

        public MagicString(String string) {
            this.string = string;
        }

        public long countVowels() {
            return string.chars().mapToObj(Character::toString).filter(VOWELS::contains).count();
        }

        @Override
        public String toString() {
            return string;
        }

        // Delegate Methods -> field
        public int length() {
            return string.length();
        }

        public boolean isEmpty() {
            return string.isEmpty();
        }

        // Could delegate many more...
    }

    static class Circle implements Shape {
        private Float radius;

        public Circle() {
        }

        public Circle(Float radius) {
            this.radius = radius;
        }

        public Float getRadius() {
            return radius;
        }

        public void setRadius(Float radius) {
            this.radius = radius;
        }

        @Override
        public String getInfo() {
            return "Circle | " + radius;
        }
    }

    static class Square implements Shape {
        private Float side;

        public Square() {
        }

        public Square(Float side) {
            this.side = side;
        }

        public Float getSide() {
            return side;
        }

        public void setSide(Float side) {
            this.side = side;
        }

        @Override
        public String getInfo() {
            return "Square | " + side;
        }
    }

    static class ColoredShape implements Shape {
        private Shape shape;
        private String color;

        public ColoredShape() {
        }

        public ColoredShape(Shape shape, String color) {
            this.shape = shape;
            this.color = color;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String getInfo() {
            return shape.getInfo() + " | " + color;
        }
    }

    static class TransparentShape implements Shape {
        private Shape shape;
        private Integer transparency;

        public TransparentShape() {
        }

        public TransparentShape(Shape shape, Integer transparency) {
            this.shape = shape;
            this.transparency = transparency;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public Integer getTransparency() {
            return transparency;
        }

        public void setTransparency(Integer transparency) {
            this.transparency = transparency;
        }

        @Override
        public String getInfo() {
            return shape.getInfo() + " | " + transparency + "%";
        }
    }

    // Static Decorator
    static class StaticColoredShape<T extends Shape> implements Shape {
        private Shape shape;
        private String color;

        public StaticColoredShape(Supplier<? extends T> supplier, String color) {
            this.shape = supplier.get();
            this.color = color;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String getInfo() {
            return shape.getInfo() + " | " + color;
        }
    }

    static class StaticTransparentShape<T extends Shape> implements Shape {
        private Shape shape;
        private Integer transparency;

        public StaticTransparentShape(Supplier<? extends T> supplier, Integer transparency) {
            this.shape = supplier.get();
            this.transparency = transparency;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public Integer getTransparency() {
            return transparency;
        }

        public void setTransparency(Integer transparency) {
            this.transparency = transparency;
        }

        @Override
        public String getInfo() {
            return shape.getInfo() + " | " + transparency + "%";
        }
    }

    // Adapter-Decorator
    static class MyStringBuilder {
        private final StringBuilder sb;

        public MyStringBuilder() {
            sb = new StringBuilder();
        }

        public MyStringBuilder(String str) {
            sb = new StringBuilder(str);
        }

        public String toString() {
            return sb.toString();
        }

        // Additional functionalities
        public MyStringBuilder appendLine(String str) {
            sb.append(str).append(System.lineSeparator());
            return this;
        }

        public MyStringBuilder concat(String str) {
            return new MyStringBuilder(sb.toString().concat(str));
        }

        // Delegated, but the return type is wrong! MyStringBuilder is not a StringBuilder, we would have to change each delegated method manually like the first append().
        public MyStringBuilder append(Object obj) {
            sb.append(obj);
            return this;
        }

        public StringBuilder append(String str) {
            return sb.append(str);
        }

        public int indexOf(String str) {
            return sb.indexOf(str);
        }
    }

    // Exercise

    interface IBird {
        String fly();
    }

    interface ILizard {
        String crawl();
    }

    static class Bird implements IBird {
        public int age;

        @Override
        public String fly() {
            return age < 10 ? "flying" : "too old";
        }
    }

    static class Lizard implements ILizard {
        public int age;

        @Override
        public String crawl() {
            return (age > 1) ? "crawling" : "too young";
        }
    }

    static class Dragon implements IBird, ILizard {
        private Bird bird;
        private Lizard lizard;
        private int age;

        public void setAge(int age) {
            this.age = bird.age = lizard.age = age;
        }

        public Bird getBird() {
            return bird;
        }

        public void setBird(Bird bird) {
            this.bird = bird;
        }

        public Lizard getLizard() {
            return lizard;
        }

        public void setLizard(Lizard lizard) {
            this.lizard = lizard;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String fly() {
            return bird.fly();
        }

        @Override
        public String crawl() {
            return lizard.crawl();
        }
    }
}
