package org.example.patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static void main(String[] args) {
        ObserverInfrastructureDemo observerInfrastructureDemo = new ObserverInfrastructureDemo();
    }

    // Observer & Observable
    static class PropertyChangedEventArgs<T> {
        public T source;
        public String propertyName;
        public Object newValue;

        public PropertyChangedEventArgs(T source, String propertyName, Object newValue) {
            this.source = source;
            this.propertyName = propertyName;
            this.newValue = newValue;
        }
    }

    interface IObserver<T> {
        void handle(PropertyChangedEventArgs<T> args);
    }

    static class Observable<T> {
        private final List<IObserver<T>> observers = new ArrayList<>();

        public void subscribe(IObserver<T> observer) {
            observers.add(observer);
        }

        protected void propertyChanged(T source, String propertyName, Object newValue) {
            observers.forEach(o -> o.handle(new PropertyChangedEventArgs<>(source, propertyName, newValue)));
        }
    }

    static class Person extends Observable<Person> {
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            if (this.age == age) {
                return;
            }
            this.age = age;
            propertyChanged(this, "age", age);
        }
    }

    static class ObserverInfrastructureDemo implements IObserver<Person> {
        public ObserverInfrastructureDemo() {
            Person person = new Person();
            person.subscribe(this);
            for (int i = 20; i < 24; i++) {
                person.setAge(i);
            }
        }

        public static void main(String[] args) {
            new ObserverInfrastructureDemo();
        }

        @Override
        public void handle(PropertyChangedEventArgs<Person> args) {
            System.out.println("New " + args.propertyName + " is now " + args.newValue);
        }
    }

}
