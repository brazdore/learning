package org.example.patterns.creational;

import java.io.*;

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
        /*public static synchronized LazySingleton getInstance() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }*/

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

}
