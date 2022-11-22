package com.example.tema04.test;

import com.example.tema04.command.configs.CommandConfig;
import com.example.tema04.command.entities.*;
import com.example.tema04.command.exceptions.CalculatorDivisionException;
import com.example.tema04.command.interfaces.Operation;
import com.example.tema04.command.repositories.CalculatorHistory;
import com.example.tema04.command.services.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.example.tema04.command")
public class CommandCalculatorServiceTest {

    private final ApplicationContext context = new AnnotationConfigApplicationContext(CommandConfig.class);
    private final CalculatorService calculatorService = context.getBean("calculatorService", CalculatorService.class);
    private final CalculatorHistory calculatorHistory = context.getBean("calculatorHistory", CalculatorHistory.class);
    private Operation operation;

    @BeforeEach
    void init() {
        calculatorService.getCalculatorHistory().clear();
    }

    @Test
    public void shouldPerformAddition() {
        operation = context.getBean("addition", Addition.class);
        operation.setValues(3D, 8D);
        Assertions.assertEquals(calculatorService.execute(operation), 11D);
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 1);
    }

    @Test
    public void shouldPerformSubtraction() {
        operation = context.getBean("subtraction", Subtraction.class);
        operation.setValues(10D, -5D);
        Assertions.assertEquals(calculatorService.execute(operation), 15D);
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 1);
    }

    @Test
    public void shouldPerformMultiplication() {
        operation = context.getBean("multiplication", Multiplication.class);
        operation.setValues(4D, 30D);
        Assertions.assertEquals(calculatorService.execute(operation), 120D);
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 1);
    }

    @Test
    public void shouldPerformDivision() {
        operation = context.getBean("division", Division.class);
        operation.setValues(100D, 100D);
        Assertions.assertEquals(calculatorService.execute(operation), 1D);
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 1);
    }

    @Test
    public void shouldThrowWhenBuildingDivisionByZero() {
        operation = context.getBean("division", Division.class);
        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                operation.setValues(1000D, 0D), "Should throw when building division by [ZERO].");
    }

    @Test
    public void shouldThrowWhenBuildingDivisionByMinusZero() {
        operation = context.getBean("division", Division.class);
        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                operation.setValues(1000D, -0D), "Should throw when building division by [ZERO].");
    }

    @Test
    public void shouldPerformExponentiation() {
        operation = context.getBean("exponentiation", Exponentiation.class);
        operation.setValues(2D, 8D);
        Assertions.assertEquals(calculatorService.execute(operation), 256D);
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 1);
    }

    @Test
    public void shouldAddToHistory() {
        operation = context.getBean("subtraction", Subtraction.class);
        operation.setValues(10D, -5D);

        for (int i = 0; i < 27; i++) {
            calculatorService.execute(operation);
        }
        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 27);

        calculatorService.getCalculatorHistory().clear();

        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 0);
    }

    @Test
    public void shouldClearHistory() {
        operation = context.getBean("addition", Addition.class);
        operation.setValues(23D, 6D);
        calculatorService.execute(operation);
        calculatorService.execute(operation);

        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 2);

        calculatorService.getCalculatorHistory().clear();

        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory().size(), 0);
    }

    @Test
    public void shouldBeSingletonBeanAtCalculatorHistory() {
        operation = context.getBean("exponentiation", Exponentiation.class);
        operation.setValues(2D, 8D);
        calculatorService.execute(operation);

        Assertions.assertEquals(calculatorService.getCalculatorHistory().getHistory(), calculatorHistory.getHistory());
    }
}
