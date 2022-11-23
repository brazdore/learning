package org.example.patterns.creational;

import com.google.common.collect.Iterables;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Supplier;

public class Singleton {
    public static void main(String[] args) throws Exception {
        // BasicSingleton instance = BasicSingleton.getInstance();
        // instance.setValue(2);
        // System.out.println(instance);

        // System.out.println("---");

        // String filename = "singleton.bin";
        // InputOutput.save(instance, filename);
        // BasicSingleton deserialized = InputOutput.read(filename); // Bad! Multiple instances of a Singleton can be created through Reflection or Serialization. Must implement custom readResolve() on BasicSingleton.
        // System.out.println(deserialized == instance);

        // System.out.println("---");

        // StaticBlockSingleton instance = StaticBlockSingleton.getInstance();
        // System.out.println(instance);

        // String filename = "singleton-enum.bin";
        // EnumSingleton instance = EnumSingleton.INSTANCE; // 42

        // instance.setValue(1); // 42 -> 1
        // System.out.println("1st: " + instance.getValue());

        // InputOutput.saveEnum(instance, filename); // Saved "with" value 1. Enums are not serialized with fields; only its name is serialized.
        // instance.setValue(2); // 1 -> 2
        // System.out.println("2nd: " + instance.getValue());

        // EnumSingleton enumSingleton = InputOutput.readEnum(filename);
        // System.out.println("3rd: " + enumSingleton.getValue()); // Prints value 2 because Singleton in JVM memory has value of 2.

        // System.out.println("---");

        // CEO ceo = new CEO();
        // CEO.setName("Takanashi Kiara");
        // CEO.setAge(26);

        // CEO ceo1 = new CEO();
        // ceo1.setName("Gawr Gura");
        // ceo1.setAge(9999);

        // System.out.println(ceo); // Both have equal data.
        // System.out.println(ceo1);

        // Printer p1 = Printer.get(Subsystem.PRIMARY);
        // Printer a1 = Printer.get(Subsystem.AUXILIARY);
        // Printer a2 = Printer.get(Subsystem.AUXILIARY); // Does not create another instance of an auxiliary printer.
        // Printer f1 = Printer.get(Subsystem.FALLBACK);

        // Tests.countAllPopulation();
        // Tests.configurableCountAllPopulation();

        Object obj = new Object();
        System.out.println(SingletonTester.isSingleton(() -> obj));
        System.out.println(SingletonTester.isSingleton(Object::new));
    }

    // Basic Singleton
    static class BasicSingleton implements Serializable {
        private static final BasicSingleton INSTANCE = new BasicSingleton();

        private int value = 0;

        private BasicSingleton() {
        }

        public static BasicSingleton getInstance() {
            return INSTANCE;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "BasicSingleton{" +
                    "value=" + value +
                    '}';
        }

        @Serial
        protected Object readResolve() { // Sets default behaviour when deserializing to an object: now it returns our constant instance, preserving the expected Singleton features.
            return INSTANCE;
        }
    }

    static class InputOutput { // Serializes and deserializes our Singleton, allowing us to create multiple instances of it.
        static void save(BasicSingleton singleton, String filename) throws Exception {
            try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(singleton);
            }
        }

        static BasicSingleton read(String filename) throws Exception {
            try (FileInputStream fileInputStream = new FileInputStream(filename);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (BasicSingleton) objectInputStream.readObject();
            }
        }

        static void saveEnum(EnumSingleton singleton, String filename) throws Exception {
            try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(singleton);
            }
        }

        static EnumSingleton readEnum(String filename) throws Exception {
            try (FileInputStream fileInputStream = new FileInputStream(filename);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (EnumSingleton) objectInputStream.readObject();
            }
        }
    }

    // Static block Singleton
    static class StaticBlockSingleton {
        private static StaticBlockSingleton instance;

        static {
            try {
                instance = new StaticBlockSingleton();
            } catch (RuntimeException e) {
                System.out.println("Error when initializing singleton: " + e);
            }
        }

        private StaticBlockSingleton() throws RuntimeException {
            System.out.println("Singleton is initializing.");
            throw new RuntimeException();
        }

        public static StaticBlockSingleton getInstance() {
            return instance;
        }
    }

    // Lazy & thread-Safe singleton
    static class LazySingleton {
        private static LazySingleton instance;

        private LazySingleton() {
            System.out.println("Initializing LazySingleton.");
        }

        // Sync whole method. Not very good.
        public static synchronized LazySingleton getInstanceBad() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }

        // Double-checked locking. A little outdated, kind of OK still.
        public static LazySingleton getInstance() {
            if (instance == null) {
                synchronized (LazySingleton.class) {
                    if (instance == null) {
                        instance = new LazySingleton();
                    }
                }
            }
            return instance;
        }
    }

    // Static nested approach. Thread-safe and simpler.
    static class InnerStaticSingleton {
        private InnerStaticSingleton() {
        }

        public static InnerStaticSingleton getInstance() {
            return Impl.INSTANCE;
        }

        private static class Impl {
            private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
        }
    }

    // Enum Singleton
    enum EnumSingleton {
        INSTANCE;

        private int value;

        EnumSingleton() {
            this.value = 42;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    // Monostate
    static class CEO {
        private static String name; // Static fields work as a Singleton for data storage. Even though we may create many instances of CEO, every instance will share the same data.
        private static int age;

        public CEO() {
        }

        public static String getName() {
            return name;
        }

        public static void setName(String name) {
            CEO.name = name;
        }

        public static int getAge() {
            return age;
        }

        public static void setAge(int age) {
            CEO.age = age;
        }

        @Override
        public String toString() {
            return "CEO{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    // Multiton
    enum Subsystem {
        PRIMARY,
        AUXILIARY,
        FALLBACK
    }

    static class Printer { // Multiple but finite number of instances. Basically, one instance of each type, "type" being defined by an enum here.
        private static final HashMap<Subsystem, Printer> instances = new HashMap<>();
        private static int count = 0;

        private Printer() {
            count++;
            System.out.println("Instances count: " + count);
        }

        public static Printer get(Subsystem subsystem) {
            if (!instances.containsKey(subsystem)) { // Lazy-loading.
                Printer instance = new Printer();
                instances.put(subsystem, instance);
                return instance;
            }
            return instances.get(subsystem);

        }
    }

    // Testability with Singletons

    interface Database {
        int getPopulation(String name);
    }

    static class SingletonDatabase implements Database {
        private static final SingletonDatabase INSTANCE = new SingletonDatabase();
        private final Dictionary<String, Integer> capitals = new Hashtable<>();
        private static int count = 0;

        private static int getCount() {
            return count;
        }

        private SingletonDatabase() {
            count++;
            System.out.println("Instance count: " + count);

            try {
                File file = new File("D:\\git-projects\\learning\\java\\design-patterns-java\\capitals.txt"); // Breaks the DIP by making high-level modules depend on low-level modules. That is, our SingletonRecordFinder depends on this line.
                Path path = Paths.get(file.getPath(), "");
                List<String> lines = Files.readAllLines(path);

                Iterables.partition(lines, 2)
                        .forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static SingletonDatabase getInstance() {
            return INSTANCE;
        }

        @Override
        public int getPopulation(String capital) {
            return capitals.get(capital);
        }
    }

    static class ConfigurableRecordFinder {
        private final Database database;

        public ConfigurableRecordFinder(Database database) {
            this.database = database;
        }

        public int getTotalPopulation(List<String> names) {
            final int[] population = {0};
            names.forEach(name -> population[0] += database.getPopulation(name));
            return population[0];
        }
    }

    static class DummyDatabase implements Database {
        private final Dictionary<String, Integer> capitals = new Hashtable<>();

        public DummyDatabase() {
            capitals.put("Holocity", 3500);
            capitals.put("Nijicapital", 3500);
        }

        @Override
        public int getPopulation(String name) {
            return capitals.get(name);
        }
    }

    static class SingletonRecordFinder {
        public int getTotalPopulation(List<String> names) {
            final int[] population = {0};
            names.forEach(name -> population[0] += SingletonDatabase.getInstance().getPopulation(name));
            return population[0];
        }
    }

    static class Tests { // Should be outside psvm method for proper @Test annotation with JUnit, but this is OK.
        public static void countAllPopulation() { // Restricted by the live data inside our capitals.txt file.
            SingletonRecordFinder singletonRecordFinder = new SingletonRecordFinder();
            int totalPopulation = singletonRecordFinder.getTotalPopulation(List.of("Tokyo", "Seoul"));
            System.out.println("Expected: 3000 | Got: " + totalPopulation);
        }

        public static void configurableCountAllPopulation() { // Good! Respects DIP and uses abstraction to enable use of dummy data for testing.
            ConfigurableRecordFinder configurableRecordFinder = new ConfigurableRecordFinder(new DummyDatabase());
            int totalPopulation = configurableRecordFinder.getTotalPopulation(List.of("Holocity", "Nijicapital"));
            System.out.println("Expected: 7000 | Got: " + totalPopulation);
        }
    }

    static class SingletonTester {
        public static boolean isSingleton(Supplier<Object> func) {
            return func.get() == func.get(); // .get() executes the supplier, generating a new instance of Object if function is (Object::new).
        }
    }
}
