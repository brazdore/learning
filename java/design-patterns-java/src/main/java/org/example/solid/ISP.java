package org.example.solid;

public class ISP { // Interface Segregation Principle

    interface Machine { // Bad! Anything that implements Machine is expected to be able to print, fax and scan.
        void print(Document d);

        void fax(Document d) throws Exception; // Bad! ModernPrinter fax() might never throw exception since it actually supports fax.

        void scan(Document d);
    }

    interface Printer { // Good! Interfaces are split by functionality, similar to SRP.
        void print(Document d);
    }

    interface Fax {
        void fax(Document d);
    }

    interface Scanner {
        void scan(Document d);
    }

    interface BetterMachine extends Printer, Fax, Scanner { // Good! Instead of implementing each interface individually, this interface bundle them all together.
    }

    static class Document {
    }

    static class ModernPrinter implements Machine {

        @Override
        public void print(Document d) {
            // OK!
        }

        @Override
        public void fax(Document d) {
            // OK!
        }

        @Override
        public void scan(Document d) {
            // OK!
        }
    }

    static class OldPrinter implements Machine {
        @Override
        public void print(Document d) {
            // OK!
        }

        @Override
        public void fax(Document d) throws Exception {
            // It does not fax.
            throw new Exception(); // If exception is thrown, since the method comes from interface implementation, interface method must also throw exception.
        }

        @Override
        public void scan(Document d) {
            // It does not scan.
        }
    }

    static class JustPrinter implements Printer {
        @Override
        public void print(Document d) {
            // OK!
        }
    }

    static class PrinterAndScanner implements Printer, Scanner {
        @Override
        public void print(Document d) {
            // OK!
        }

        @Override
        public void scan(Document d) {
            // OK!
        }
    }

    static class CompleteMachine implements BetterMachine {
        private Printer printer;
        private Fax fax;
        private Scanner scanner;

        @Override
        public void print(Document d) {
            printer.print(d);
        }

        @Override
        public void fax(Document d) {
            fax.fax(d);
        }

        @Override
        public void scan(Document d) {
            scanner.scan(d);
        }
    }

}
