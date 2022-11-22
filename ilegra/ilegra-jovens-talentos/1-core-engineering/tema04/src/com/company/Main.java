package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

        Runnable evenGen = () -> {
            while (true) {
                int random = ThreadLocalRandom.current().nextInt();
                int evenToPrint = random % 2 == 0 ? random : (random / 2) * 2;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ANSI_BLUE + "EVEN: " + evenToPrint);
            }
        };

        Runnable oddGen = () -> {
            while (true) {
                int random = ThreadLocalRandom.current().nextInt();
                int oddToPrint = random % 2 == 1 || random % 2 == -1 ? random : random + 1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ANSI_RED + "ODD: " + oddToPrint);
            }
        };

        Thread evenThread = new Thread(evenGen);
        evenThread.start();

        Thread oddThread = new Thread(oddGen);
        oddThread.start();
    }
}
