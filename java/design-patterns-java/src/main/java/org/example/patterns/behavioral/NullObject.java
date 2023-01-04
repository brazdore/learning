package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.lang.reflect.Proxy;

public class NullObject {
    public static void main(String[] args) throws Exception {
        Log coloredLogging = new ColoredLogging();
        BankAccount acc1 = new BankAccount(coloredLogging);
        acc1.deposit(500);
        acc1.withdraw(600);

        Line.split();

        BankAccount acc2 = new BankAccount(null);
        try {
            acc2.deposit(500);
        } catch (NullPointerException ex) {
            System.out.println(AnsiColor.RED + ex.getMessage());
            Line.reset();
        }

        Line.split();

        BankAccount acc3 = new BankAccount(new NullLogging()); // Null Object
        acc3.deposit(500);
        acc3.withdraw(600);
        System.out.println(AnsiColor.PURPLE + acc3);

        Line.split();

        BankAccount acc4 = new BankAccount(noOp(Log.class)); // Null Object
        acc4.deposit(500);
        acc4.withdraw(600);
        System.out.println(AnsiColor.CYAN + acc4);

        Line.split();

        Account acc = new Account(new NullLog());
        acc.someOperation();
    }

    interface Log {
        void info(String msg);

        void warn(String msg);
    }

    static class ColoredLogging implements Log {
        @Override
        public void info(String msg) {
            System.out.println(AnsiColor.GREEN + "INFO: " + msg);
            Line.reset();
        }

        @Override
        public void warn(String msg) {
            System.out.println(AnsiColor.YELLOW + "WARN: " + msg);
            Line.reset();
        }
    }

    static class NullLogging implements Log {
        @Override
        public void info(String msg) {

        }

        @Override
        public void warn(String msg) {

        }
    }

    static class BankAccount {
        private int balance;
        private final Log log;

        public BankAccount(Log log) {
            this.log = log;
        }

        public void deposit(int amount) {
            balance += amount;
            log.info("Deposited: " + amount);
        }

        public void withdraw(int amount) {
            if (amount > balance) {
                log.warn("Not enough money! Balance: " + balance + " | Withdraw: " + amount);
                return;
            }
            balance -= amount;
            log.info("Withdrew: " + amount);
        }

        @Override
        public String toString() {
            return "BankAccount{" +
                    "balance=" + balance +
                    '}';
        }
    }

    // Dynamic Null Object
    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf) { // Use of Proxy to create a Null Object of type T at runtime. Though cleaner & more versatile, it is computally intensive.
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[]{itf},
                (proxy, method, args) ->
                {
                    if (method.getReturnType().equals(Void.TYPE)) {
                        return null;
                    } else {
                        return method.getReturnType().getConstructor().newInstance();
                    }
                });
    }

    // Exercise
    interface LogExercise {
        // max # of elements in the log
        int getRecordLimit();

        // number of elements already in the log
        int getRecordCount();

        // expected to increment record count
        void logInfo(String message);
    }

    static class Account {
        private final LogExercise log;

        public Account(LogExercise log) {
            this.log = log;
        }

        public void someOperation() throws Exception {
            int c = log.getRecordCount();
            log.logInfo("Performing an operation");
            if (c + 1 != log.getRecordCount())
                throw new Exception();
            if (log.getRecordCount() >= log.getRecordLimit())
                throw new Exception();
        }
    }

    static class NullLog implements LogExercise {
        private int recordCount = 0;

        @Override
        public int getRecordLimit() {
            return recordCount + 1;
        }

        @Override
        public int getRecordCount() {
            return ++recordCount;
        }

        @Override
        public void logInfo(String message) {

        }
    }
}
