package com.example.tema08;

import com.example.tema08.entities.*;
import com.example.tema08.repositories.CalculatorHistory;
import com.example.tema08.services.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService(new CalculatorHistory());
    private Operation operation = null;

    @BeforeEach
    void clear() {
        calculatorService.getHistory().clear();
        operation = null;
    }

    @Test
    public void shouldPerformAddition() {
        operation = new Addition(2D, 2D);
        Assertions.assertEquals(4D, operation.execute());
    }

    @Test
    public void shouldPerformSubtraction() {
        operation = new Subtraction(2D, 2D);
        Assertions.assertEquals(0D, operation.execute());
    }

    @Test
    public void shouldPerformMultiplication() {
        operation = new Multiplication(2D, 2D);
        Assertions.assertEquals(4D, operation.execute());
    }

    @Test
    public void shouldPerformDivision() {
        operation = new Division(2D, 2D);
        Assertions.assertEquals(1D, operation.execute());
    }

    @Test
    public void shouldPerformExponentiation() {
        operation = new Exponentiation(2D, 8D);
        Assertions.assertEquals(256D, operation.execute());
    }

    @Test
    public void shouldAssertBothValid() {
        Assertions.assertTrue(calculatorService.areBothValid(String.valueOf(2D), String.valueOf(-10D)));
        Assertions.assertTrue(calculatorService.areBothValid(String.valueOf(-5D), String.valueOf(0D)));
        Assertions.assertTrue(calculatorService.areBothValid(String.valueOf(500D), String.valueOf(-329)));
        Assertions.assertTrue(calculatorService.areBothValid(String.valueOf(+0D), String.valueOf(-0D)));
    }

    @Test
    public void shouldGetAndAddToHistory() {
        Assertions.assertTrue(calculatorService.getHistory().isEmpty());

        operation = new Exponentiation(2D, 8D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(0));
        Assertions.assertEquals(1, calculatorService.getHistory().size());

        operation = new Addition(2D, 2D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(1));
        Assertions.assertEquals(2, calculatorService.getHistory().size());

        operation = new Subtraction(15D, 7D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(2));
        Assertions.assertEquals(3, calculatorService.getHistory().size());
    }

    @Test
    public void shouldClearHistory() {
        Assertions.assertTrue(calculatorService.getHistory().isEmpty());

        operation = new Exponentiation(2D, 8D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(0));
        Assertions.assertEquals(1, calculatorService.getHistory().size());

        operation = new Addition(2D, 2D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(1));
        Assertions.assertEquals(2, calculatorService.getHistory().size());

        operation = new Subtraction(15D, 7D);
        calculatorService.execute(operation);
        Assertions.assertEquals(operation, calculatorService.getHistory().get(2));
        Assertions.assertEquals(3, calculatorService.getHistory().size());

        calculatorService.clear();
        Assertions.assertTrue(calculatorService.getHistory().isEmpty());
    }
}
