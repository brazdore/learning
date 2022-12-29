package org.example.patterns.behavioral;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mediator {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();

        Person rosemi = new Person("Rosemi Lovelock");
        Person finana = new Person("Finana Ryugu");

        room.join(rosemi); // No message is shown because the room is empty.
        room.join(finana);

        rosemi.say("hey buds");
        finana.say("hey rosemiii");

        Person enna = new Person("Enna Alouette");
        room.join(enna);
        enna.say("f****** b****");

        finana.privateMessage("Enna Alouette", "o-o");

        Line.split();

        EventBroker broker = new EventBroker();
        FootballPlayer player = new FootballPlayer(broker, "jones");
        FootballCoach coach = new FootballCoach(broker);

        player.score();
        player.score();
        player.score();

        Line.split();

        Participant p1 = new Participant("Millie Parfait");
        Participant p2 = new Participant("Elira Pendora");

        Game game = new Game();
        game.add(p1, p2);

        p1.say(4);
        System.out.println(AnsiColor.RED + p2); // 0 - 4
        System.out.print(AnsiColor.RESET);
        p2.say(8);
        System.out.println(AnsiColor.BLUE + p1); // 8 - 4
        p1.say(2);
        System.out.println(AnsiColor.RED + p2); // 8 - 6
        System.out.print(AnsiColor.RESET);
        p2.say(2);
        System.out.println(AnsiColor.BLUE + p1); // 10 - 6
        System.out.print(AnsiColor.RESET);
    }

    // Chat Room
    static class Person {
        public String name;
        public ChatRoom room;
        private final List<String> chatLog = new ArrayList<>();

        public Person(String name) {
            this.name = name;
        }

        public void receive(String sender, String message) {
            String s = sender + ": '" + message + "'";
            System.out.println("[" + name + "'s chat session] " + s);
            chatLog.add(s);
        }

        public void say(String message) {
            room.broadcast(name, message);
        }

        public void privateMessage(String who, String message) {
            room.message(name, who, message);
        }
    }

    static class ChatRoom { // Mediator itself.
        private final List<Person> people = new ArrayList<>();

        public void broadcast(String source, String message) {
            for (Person person : people)
                if (!person.name.equals(source)) {
                    person.receive(source, message);
                }
        }

        public void join(Person p) {
            String joinMsg = p.name + " joins the chat";
            broadcast("room", joinMsg);

            p.room = this;
            people.add(p);
        }

        public void message(String source, String destination, String message) {
            people.stream()
                    .filter(p -> p.name.equals(destination))
                    .findFirst()
                    .ifPresent(person -> person.receive(source, message));
        }
    }

    // RX Event Broker
    static class EventBroker extends Observable<Integer> {
        private final List<Observer<? super Integer>> observers = new ArrayList<>();

        @Override
        protected void subscribeActual(Observer<? super Integer> observer) {
            observers.add(observer);
        }

        public void publish(int n) {
            for (Observer<? super Integer> o : observers) {
                o.onNext(n);
            }
        }
    }

    static class FootballPlayer {
        private int goalsScored = 0;
        private final EventBroker broker;
        public String name;

        public FootballPlayer(EventBroker broker, String name) {
            this.broker = broker;
            this.name = name;
        }

        public void score() {
            broker.publish(++goalsScored);
        }
    }

    static class FootballCoach {
        public FootballCoach(EventBroker broker) {
            broker.subscribe(i -> System.out.println("Hey, you scored " + i + " goals!"));
        }
    }

    // Exercise

    static class Participant {
        private Integer value = 0;
        private final String name;
        protected Game game;

        public Participant(String name) {
            this.name = name;
        }

        public void say(int n) {
            System.out.println(AnsiColor.YELLOW + name + " said value: " + n);
            Line.reset();

            game.participants.stream()
                    .filter(p -> p != this)
                    .forEach(p -> p.value += n);

            game.finished();
        }

        @Override
        public String toString() {
            return name + " has value: " + value;
        }
    }

    static class Game {
        private final List<Participant> participants = new ArrayList<>();

        public void add(Participant... participants) {
            this.participants.addAll(Arrays.stream(participants).toList());
            Arrays.stream(participants).forEach(p -> p.game = this);
        }

        public void finished() {
            participants.stream()
                    .filter(p -> p.value >= 10)
                    .findAny()
                    .ifPresent(p -> System.out.println(AnsiColor.CYAN + "The game is finished! " + p.name +
                            " has won with " + p.value + " value."));
        }
    }

}
