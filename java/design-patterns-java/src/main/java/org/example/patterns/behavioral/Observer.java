package org.example.patterns.behavioral;

import org.example.utils.Line;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Observer {
    public static void main(String[] args) {
        ObserverInfrastructureDemo observerInfrastructureDemo = new ObserverInfrastructureDemo();
        Line.split();

        HandmadeEventsDemo.main(new String[]{"a", "b", "c"});
        Line.split();

        Game game = new Game();
        Rat r1 = new Rat(game);
        Rat r2 = new Rat(game);
        Rat r3 = new Rat(game);
        r2.close();
        System.out.println(r3.attack);
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

    // Event Class
    static class Event<T> {
        private int count = 0;
        private final Map<Integer, Consumer<T>> handlers = new HashMap<>();

        public Subscription addHandler(Consumer<T> handler) {
            int i = count;
            handlers.put(count++, handler);
            return new Subscription(this, i);
        }

        public void fire(T args) {
            handlers.values().forEach(h -> h.accept(args));
        }

        public class Subscription implements AutoCloseable {
            private final Event<T> event;
            private final int id;

            public Subscription(Event<T> event, int id) {
                this.event = event;
                this.id = id;
            }

            @Override
            public void close() {
                event.handlers.remove(id);
            }
        }
    }

    static class PropertyChangeEventArgs {
        public Object source;
        public String propertyName;

        public PropertyChangeEventArgs(Object source, String propertyName) {
            this.source = source;
            this.propertyName = propertyName;
        }
    }

    static class Human {
        public Event<PropertyChangeEventArgs> propertyChange = new Event<>();

        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            if (this.age == age) {
                return;
            }

            boolean oldCanVote = getCanVote();

            this.age = age;
            propertyChange.fire(new PropertyChangeEventArgs(this, "age"));

            if (oldCanVote != getCanVote()) {
                propertyChange.fire(new PropertyChangeEventArgs(this, "canVote"));
            }
        }

        public boolean getCanVote() { // Depends on field age, therefore change notifications must be inside setAge(); which is really bad for scaling.
            return age >= 18;
        }
    }

    static class HandmadeEventsDemo {
        public static void main(String[] args) {
            Human human = new Human();
            Event<PropertyChangeEventArgs>.Subscription sub = human.propertyChange.addHandler(
                    x -> System.out.println("Person's " + x.propertyName + " has changed"));
            human.setAge(17);
            human.setAge(18);
            sub.close();
            human.setAge(19);
        }
    }

    // Exercise
    static class EventExercise<T> {
        private final List<BiConsumer<Object, T>> consumers = new ArrayList<>();

        public void subscribe(BiConsumer<Object, T> consumer) {
            consumers.add(consumer);
        }

        public void invoke(Object sender, T arg) {
            consumers.forEach(c -> c.accept(sender, arg));
        }
    }

    static class Game {
        public EventExercise<Void> ratEnters = new EventExercise<>();
        public EventExercise<Void> ratDies = new EventExercise<>();
        public EventExercise<Rat> notifyRat = new EventExercise<>();
    }

    static class Rat implements Closeable {
        private final Game game;
        public int attack = 1;

        public Rat(Game game) { // When a new Rat comes to play and it is not the first Rat, it will fire a ratEnters() event which will increase its attack by 1 AND fire a notifyRat() event, increasing each other Rat's attack by 1 as well.
            this.game = game;
            game.ratEnters.subscribe((sender, arg) -> {
                if (sender != this) {
                    attack++;
                    game.notifyRat.invoke(this, (Rat) sender);
                }
            });
            game.notifyRat.subscribe((sender, rat) -> {
                if (rat == this) {
                    attack++;
                }
            });
            game.ratDies.subscribe((sender, arg) -> --attack);
            game.ratEnters.invoke(this, null);
        }

        @Override
        public void close() {
            game.ratDies.invoke(this, null);
        }
    }
}
