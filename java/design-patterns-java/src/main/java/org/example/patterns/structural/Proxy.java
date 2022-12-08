package org.example.patterns.structural;

import org.example.utils.Line;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.patterns.structural.Proxy.DynamicLoggingUtils.withLogging;

public class Proxy {
    public static void main(String[] args) {
        Car car = new Car(new Driver(12));
        car.drive();

        Car carProxy = new CarProxy(new Driver(12));
        carProxy.drive();

        Line.split();

        Creature creature = new Creature();
        creature.setAgility(10);
        System.out.println(creature.getAgility());

        Line.split();

        Person person = new Person();
        Human logged = withLogging(person, Human.class);
        logged.walk();
        logged.talk();
        logged.talk();

        System.out.println(logged);

        Line.split();

        PersonExercise personExercise = new PersonExercise(16);
        System.out.println(personExercise.drinkAndDrive());

        ResponsiblePerson responsiblePerson = new ResponsiblePerson(15);
        System.out.println(responsiblePerson.drinkAndDrive());
    }

    // Protection Proxy
    interface Drivable {
        void drive();
    }

    static class Car implements Drivable { // A Driver should not be able to drive when younger than 18, but Car should not check that condition.
        protected final Driver driver;

        public Car(Driver driver) {
            this.driver = driver;
        }

        @Override
        public void drive() {
            System.out.println("Drive: Car");
        }
    }

    static class Driver {
        private final int age;

        public Driver(int age) {
            this.age = age;
        }
    }

    static class CarProxy extends Car { // CarProxy is a Car through inheritance and checks for age condition.
        public CarProxy(Driver driver) {
            super(driver);
        }

        @Override
        public void drive() {
            if (driver.age >= 18) {
                super.drive();
            } else System.out.println("Too young to drive!");
        }
    }

    // Property Proxy
    static class Property<T> { // Class to represent generic field of type T. To be used instead of actual type T on instance variables.
        private T value;

        public Property(T value) {
            this.value = value;
        }

        public void setValue(T value) {
            // do some logging here
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Property<?> property = (Property<?>) o;
            return value.equals(property.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    static class Creature {
        private final Property<Integer> agility = new Property<>(null);

        public void setAgility(Integer value) {
            agility.setValue(value);
        }

        public Integer getAgility() {
            return agility.getValue();
        }
    }

    // Dynamic Proxy
    static class LoggingHandler implements InvocationHandler {
        private final Object target;
        private final Map<String, Integer> calls = new HashMap<>();

        LoggingHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();

            if (name.contains("toString")) {
                return calls.toString();
            }

            calls.merge(name, 1, Integer::sum);
            return method.invoke(target, args);
        }
    }

    interface Human {
        void walk();

        void talk();
    }

    static class Person implements Human {
        @Override
        public void walk() {
            System.out.println("I am walking");
        }

        @Override
        public void talk() {
            System.out.println("I am talking");
        }
    }

    static class DynamicLoggingUtils {
        @SuppressWarnings("unchecked")
        public static <T> T withLogging(T target, Class<T> itf) {
            return (T) java.lang.reflect.Proxy.newProxyInstance(
                    itf.getClassLoader(),
                    new Class<?>[]{itf},
                    new LoggingHandler(target));
        }
    }

    // Exercise

    static class PersonExercise {
        private int age;

        public PersonExercise(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String drink() {
            return "drinking";
        }

        public String drive() {
            return "driving";
        }

        public String drinkAndDrive() {
            return "driving while drunk";
        }
    }

    static class ResponsiblePerson extends PersonExercise {
        public ResponsiblePerson(int age) {
            super(age);
        }

        @Override
        public String drink() {
            if (getAge() < 18) {
                return "too young";
            }
            return super.drink();
        }

        @Override
        public String drive() {
            if (getAge() < 16) {
                return "too young";
            }
            return super.drive();
        }

        @Override
        public String drinkAndDrive() {
            return "dead";
        }
    }
}
