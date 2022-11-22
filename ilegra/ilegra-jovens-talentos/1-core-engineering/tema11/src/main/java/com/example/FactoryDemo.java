package com.example;

import com.example.factories.IdolFactory;
import com.example.interfaces.Idol;

public class FactoryDemo {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {

        Idol pure = IdolFactory.getIdol("PURE");
        Idol corrupted = IdolFactory.getIdol("CORRUPTED");
        Idol haachama = IdolFactory.getIdol("HAACHAMA");

        System.out.println(ANSI_BLUE + pure.sing());
        System.out.println(ANSI_YELLOW + corrupted.sing());
        System.out.println(ANSI_RED + haachama.sing());
        System.out.println(ANSI_RESET);
    }
}
