package org.example.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SRP { // Single Responsability Principle

    public static void main(String[] args) throws IOException {
        Journal j = new Journal();
        j.addEntry("Rosemi-sama is very cute.");
        j.addEntry("I'm Pomu.");
        System.out.println(j);

        Persistance p = new Persistance();
        String filename = "D:\\temp\\journal.txt";
        p.saveToFile(j, filename, true);

        Runtime.getRuntime().exec("notepad.exe " + filename);
    }

    static class Journal {
        private static int count = 0;
        private final List<String> entries = new ArrayList<>();

        public void addEntry(String s) {
            entries.add("" + (++count) + ": " + s);
        }

        public void removeEntry(int index) {
            entries.remove(index);
        }

        @Override
        public String toString() {
            return String.join(System.lineSeparator(), entries);
        }
    }

    static class Persistance {
        public void saveToFile(Journal j, String filename, boolean overwrite) throws FileNotFoundException {
            if (overwrite || new File(filename).exists()) {
                try (PrintStream out = new PrintStream(filename)) {
                    out.println(j);
                }
            }
        }
    }
}
