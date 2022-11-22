package com.example.tema04.command.configs;

import com.example.tema04.command.entities.*;
import com.example.tema04.command.repositories.CalculatorHistory;
import com.example.tema04.command.services.CalculatorService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.example.tema04.command")
public class CommandConfig {

    @Bean(value = "calculatorService")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CalculatorService calculatorService() {
        return new CalculatorService(calculatorHistory());
    }

    @Bean(value = "calculatorHistory")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CalculatorHistory calculatorHistory() {
        return new CalculatorHistory();
    }

    @Bean(value = "addition")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Addition addition() {
        return new Addition();
    }

    @Bean(value = "subtraction")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Subtraction subtraction() {
        return new Subtraction();
    }

    @Bean(value = "multiplication")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Multiplication multiplication() {
        return new Multiplication();
    }

    @Bean(value = "division")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Division division() {
        return new Division();
    }

    @Bean(value = "exponentiation")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Exponentiation exponentiation() {
        return new Exponentiation();
    }
}
