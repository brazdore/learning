package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    public static void main(String[] args) {
        BankAccount ba = new BankAccount(100);
        BankAccountMemento m1 = ba.deposit(50); // 150
        BankAccountMemento m2 = ba.deposit(25); // 175
        System.out.println(AnsiColor.CYAN + ba);

        // restore to m1
        ba.restore(m1);
        System.out.println(AnsiColor.YELLOW + ba);

        // restore to m2
        ba.restore(m2);
        System.out.println(AnsiColor.GREEN + ba);

        Line.split();
        TokenMachine tm = new TokenMachine();
        MementoExercise mm1 = tm.addToken(123);
        System.out.println(AnsiColor.BLUE + mm1); /// 123
        MementoExercise mm2 = tm.addToken(456);
        System.out.println(AnsiColor.RED + mm2); /// 123 456

        Line.reset();
    }

    static class BankAccountMemento { // Storing many Mementos might cost a lot of memory. Storing commands instead of states might be best, therefore Command.
        private final int balance;

        public BankAccountMemento(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }
    }

    static class BankAccount {
        private int balance;

        public BankAccount(int balance) {
            this.balance = balance;
        }

        public BankAccountMemento deposit(int amount) {
            balance += amount;
            return new BankAccountMemento(balance);
        }

        public void restore(BankAccountMemento m) {
            balance = m.getBalance();
        }

        @Override
        public String toString() {
            return "BankAccount{" +
                    "balance=" + balance +
                    '}';
        }
    }

    // Exercise
    static class Token {
        public int value;

        public Token(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "value=" + value +
                    '}';
        }
    }

    static class MementoExercise {
        public List<Token> tokens = new ArrayList<>();

        @Override
        public String toString() {
            return "MementoExercise{" +
                    "tokens=" + tokens +
                    '}';
        }
    }

    static class TokenMachine { // Since the state is an collection of objects, we must store a new collection of new objects with the same value inside our Memento.
        public List<Token> tokens = new ArrayList<>();

        public MementoExercise addToken(int value) {
            return addToken(new Token(value));
        }

        public MementoExercise addToken(Token token) {
            tokens.add(token);
            MementoExercise memento = new MementoExercise();

            memento.tokens = tokens.stream()
                    .map(t -> new Token(t.value))
                    .toList();

            return memento;
        }

        public void revert(MementoExercise m) {
            tokens = m.tokens.stream()
                    .map(t -> new Token(t.value))
                    .toList();
        }

        @Override
        public String toString() {
            return "TokenMachine{" +
                    "tokens=" + tokens +
                    '}';
        }
    }
}
