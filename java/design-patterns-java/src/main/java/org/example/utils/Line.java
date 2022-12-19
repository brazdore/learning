package org.example.utils;

public class Line {

    public static void split() {
        System.out.println(AnsiColor.GREEN + "---");
        System.out.printf(AnsiColor.RESET);
    }

    public static void reset() {
        System.out.printf(AnsiColor.RESET);
    }
}
