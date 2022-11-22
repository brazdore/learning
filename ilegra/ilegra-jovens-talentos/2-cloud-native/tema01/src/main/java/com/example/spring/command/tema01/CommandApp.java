package com.example.spring.command.tema01;

import com.example.spring.command.tema01.configs.CommandConfig;
import com.example.spring.command.tema01.entities.*;
import com.example.spring.command.tema01.interfaces.Operation;
import com.example.spring.command.tema01.services.CalculatorService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class CommandApp {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CommandConfig.class);

        CalculatorService calculatorService = context.getBean("calculatorService", CalculatorService.class);
        Operation addition = context.getBean("addition", Addition.class);
        Operation subtraction = context.getBean("subtraction", Subtraction.class);
        Operation multiplication = context.getBean("multiplication", Multiplication.class);
        Operation division = context.getBean("division", Division.class);
        Operation exponentiation = context.getBean("exponentiation", Exponentiation.class);

        addition.setValues(5D, 3D);
        subtraction.setValues(10D, 8D);
        multiplication.setValues(2D, 15D);
        division.setValues(10D, 10D);
        exponentiation.setValues(2D, 8D);

        System.out.println(ANSI_RED + calculatorService.execute(addition));
        System.out.println(ANSI_BLUE + calculatorService.execute(subtraction));
        System.out.println(ANSI_YELLOW + calculatorService.execute(multiplication));
        System.out.println(ANSI_GREEN + calculatorService.execute(division));
        System.out.println(ANSI_CYAN + calculatorService.execute(exponentiation));

        System.out.println(ANSI_PURPLE + calculatorService.getCalculatorHistory().getHistory());
        System.out.print(ANSI_RESET);
    }
}
