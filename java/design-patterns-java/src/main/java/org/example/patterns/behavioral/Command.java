package org.example.patterns.behavioral;

import com.google.common.collect.Lists;
import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.List;

public class Command {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(500);
        List<BankAccountCommand> bankAccountCommands = List.of(new BankAccountCommand(account, 300, Action.DEPOSIT),
                new BankAccountCommand(account, 1300, Action.WITHDRAW),
                new BankAccountCommand(account, 1, Action.WITHDRAW));
        bankAccountCommands.forEach(BankAccountCommand::call);

        Line.split();
        Lists.reverse(bankAccountCommands).forEach(BankAccountCommand::undo);

        Line.split();

        Account acc = new Account(100);
        CommandExercise c1 = new CommandExercise(CommandExercise.Action.WITHDRAW, 50);
        CommandExercise c2 = new CommandExercise(CommandExercise.Action.DEPOSIT, 100);
        CommandExercise c3 = new CommandExercise(CommandExercise.Action.WITHDRAW, 1000);

        acc.process(c1);
        System.out.println(AnsiColor.YELLOW + acc); // 50
        acc.process(c2);
        System.out.println(AnsiColor.YELLOW + acc); // 150
        acc.process(c3);
        System.out.println(AnsiColor.YELLOW + acc); // Fail
        Line.reset();
    }

    static class BankAccount {
        private int balance;
        private static final int LOAN_LIMIT = -500;

        public BankAccount(int balance) {
            this.balance = balance;
        }

        private boolean deposit(int amount) {
            balance += amount;
            System.out.println(AnsiColor.BLUE + "Deposited: " + amount);
            System.out.println("New Balance: " + balance);
            Line.reset();
            return true;
        }

        private boolean withdraw(int amount) {
            if (balance - amount >= LOAN_LIMIT) {
                balance -= amount;
                System.out.println(AnsiColor.RED + "Withdrew: " + amount);
                System.out.println("New Balance: " + balance);
                Line.reset();
                return true;
            } else {
                System.out.println(AnsiColor.PURPLE + "Could not withdraw: " + amount + AnsiColor.RESET);
                return false;
            }
        }
    }

    interface CommandInterface {
        void call();

        void undo();
    }

    enum Action {
        WITHDRAW, DEPOSIT
    }

    static class BankAccountCommand implements CommandInterface {
        private final BankAccount bankAccount;
        private final int amount;
        private final Action action;
        private boolean executed;

        public BankAccountCommand(BankAccount bankAccount, int amount, Action action) {
            this.bankAccount = bankAccount;
            this.amount = amount;
            this.action = action;
        }

        @Override
        public void call() {
            switch (action) {
                case DEPOSIT -> executed = bankAccount.deposit(amount);
                case WITHDRAW -> executed = bankAccount.withdraw(amount);
            }
        }

        @Override
        public void undo() {
            if (!executed) {
                return;
            }

            switch (action) {
                case DEPOSIT -> bankAccount.withdraw(amount);
                case WITHDRAW -> bankAccount.deposit(amount);
            }
        }
    }

    // Exercise
    static class CommandExercise {
        enum Action {
            DEPOSIT, WITHDRAW
        }

        public Action action;
        public int amount;
        public boolean success;

        public CommandExercise(Action action, int amount) {
            this.action = action;
            this.amount = amount;
        }
    }

    static class Account {
        public int balance;

        public Account(int balance) {
            this.balance = balance;
        }

        public void process(CommandExercise c) {
            switch (c.action) {
                case DEPOSIT -> {
                    balance += c.amount;
                    c.success = true;
                }
                case WITHDRAW -> {
                    if (balance >= c.amount) {
                        balance -= c.amount;
                        c.success = true;
                    } else System.out.println("Cannot withdraw " + c.amount);
                }
            }
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    '}';
        }
    }
}
