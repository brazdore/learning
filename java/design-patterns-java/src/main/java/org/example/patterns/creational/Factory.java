package org.example.patterns.creational;

import org.javatuples.Pair;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Factory {

    public static void main(String[] args) throws Exception {
        Point cartesianPoint = Point.newCartesianPoint(2D, 3D);
        Point polarPoint = Point.newPolarPoint(2D, 3D);

        Point cartesianPoint1 = Point.PointFactory.newCartesianPoint(2D, 3D);
        Point polarPoint1 = Point.PointFactory.newPolarPoint(2D, 3D);

        HotDrinkMachine machine = new HotDrinkMachine();
        IHotDrink drink = machine.makeDrink(HotDrink.TEA, 200);
        drink.consume();
    }

    enum PointSystem {
        CARTESIAN, POLAR
    }

    enum HotDrink {
        TEA, COFFEE, COCOA
    }

    // Abstract Factory
    interface IHotDrink {
        void consume();
    }

    interface IHotDrinkFactory {
        IHotDrink prepare(int amount);
    }

    // Method Factory & Factory Class
    static class Point {
        private Double x, y;

        private Point(Double a, Double b, PointSystem system) { // Bad!
            if (system == PointSystem.CARTESIAN) {
                this.x = a;
                this.y = b;
            } else if (system == PointSystem.POLAR) {
                this.x = a * Math.cos(b);
                this.y = a * Math.sin(b);
            }
        }

        private Point(Double a, Double b) { // Too generic. It would be nice if parameters names indicated what system it is: x & y OR rho & theta.
            this.x = a;
            this.y = b;
        }

        public static Point newCartesianPoint(Double x, Double y) { // Good! A private constructor forces user to instantiate through our specialized static Factory Methods.
            return new Point(x, y);
        }

        public static Point newPolarPoint(Double rho, Double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        static class PointFactory { // Could be named just Factory, but that is the name of the base class already. It is an inner class to not change access modifier on private constructor, as to not allow multiple methods of instantiation.
            public static Point newCartesianPoint(Double x, Double y) {
                return new Point(x, y);
            }

            public static Point newPolarPoint(Double rho, Double theta) {
                return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
            }
        }
    }

    // Abstract Factory
    static class Tea implements IHotDrink {
        @Override
        public void consume() {
            System.out.println("Tea was consumed.");
        }
    }

    static class Coffee implements IHotDrink {
        @Override
        public void consume() {
            System.out.println("Coffee was consumed.");
        }
    }

    static class TeaFactory implements IHotDrinkFactory { // Factories are not called directly, instead they are abstracted and used in the HotDrinkMachine drink making.
        @Override
        public IHotDrink prepare(int amount) {
            System.out.println(
                    "Put leaves, boil water, pour " + amount + "ml."
            );
            return new Tea();
        }
    }

    static class CoffeeFactory implements IHotDrinkFactory {
        @Override
        public IHotDrink prepare(int amount) {
            System.out.println(
                    "Grind beans, boil water, pour " + amount + " ml."
            );
            return new Coffee();
        }
    }

    static class HotDrinkMachine {
        private final List<Pair<String, IHotDrinkFactory>> factories = new ArrayList<>();

        public HotDrinkMachine() {
            factories.add(Pair.with("TEA", new TeaFactory()));
            factories.add(Pair.with("COFFEE", new CoffeeFactory()));
        }

        public IHotDrink makeDrink(HotDrink drink, int amount) throws Exception {
            if (amount <= 0) {
                throw new InvalidParameterException("Some water must be used, otherwise it is NOT a DRINK!");
            }
            String drinkName = drink.toString();
            return factories.stream()
                    .parallel()
                    .filter(x -> Objects.equals(x.getValue0(), drinkName))
                    .findFirst()
                    .orElseThrow(() -> new InvalidParameterException(drinkName + " is a wise choice. Sadly the machine does not support making it."))
                    .getValue1()
                    .prepare(amount);
        }
    }

    // Exercise
    static class Person {
        public int id;
        public String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class PersonFactory {
        private Integer sum = 0;

        public Person createPerson(String name) {
            Person person = new Person(sum, name);
            sum++;
            return person;
        }
    }
}
