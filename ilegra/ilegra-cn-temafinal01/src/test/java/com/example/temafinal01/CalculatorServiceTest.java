package com.example.temafinal01;

import com.example.temafinal01.exceptions.CalculatorDivisionException;
import com.example.temafinal01.exceptions.CalculatorInputException;
import com.example.temafinal01.repositories.CalculatorRepository;
import com.example.temafinal01.services.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void init() {
        calculatorService = new CalculatorService(new CalculatorRepository());
    }

    @Test
    public void shouldPerformAddition() {
        Assertions.assertEquals(350D, calculatorService.addition("345", "5"));
    }

    @Test
    public void shouldThrowAtAddition() {
        Assertions.assertThrows(CalculatorInputException.class, () ->
                calculatorService.addition("5", "super nenechi"), "(Addition) Should throw when [STRING] cannot be converted to [DOUBLE].");
    }

    @Test
    public void shouldPerformSubtraction() {
        Assertions.assertEquals(350D, calculatorService.subtraction("345", "-5"));
    }

    @Test
    public void shouldThrowAtSubtraction() {
        Assertions.assertThrows(CalculatorInputException.class, () ->
                calculatorService.subtraction("5", "cho'gath"), "(Subtraction) Should throw when [STRING] cannot be converted to [DOUBLE].");
    }

    @Test
    public void shouldPerformMultiplication() {
        Assertions.assertEquals(-30D, calculatorService.multiplication("15", "-2"));
    }

    @Test
    public void shouldThrowAtMultiplication() {
        Assertions.assertThrows(CalculatorInputException.class, () ->
                calculatorService.multiplication("5", "stan moona"), "(Multiplication) Should throw when [STRING] cannot be converted to [DOUBLE].");
    }

    @Test
    public void shouldPerformDivision() {
        Assertions.assertEquals(20D, calculatorService.division("100", "5"));
    }

    @Test
    public void shouldThrowAtDivision() {
        Assertions.assertThrows(CalculatorInputException.class, () ->
                calculatorService.division("5", "hello world"), "(Division) Should throw when [STRING] cannot be converted to [DOUBLE].");
    }

    @Test
    public void shouldThrowAtDivisionByZero() {
        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                calculatorService.division("5", "0"), "(Division) Should throw when division by [ZERO] is called.");
    }

    @Test
    public void shouldThrowAtDivisionByMinusZero() {
        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                calculatorService.division("5", "-0"), "(Division) Should throw when division by [ZERO] is called.");
    }

    @Test
    public void shouldPerformExponentiation() {
        Assertions.assertEquals(256D, calculatorService.exponentiation("2", "8"));
    }

    @Test
    public void shouldThrowAtExponentiation() {
        Assertions.assertThrows(CalculatorInputException.class, () ->
                calculatorService.exponentiation("5", "clearly not a number"), "(Exponentiation) Should throw when [STRING] cannot be converted to [DOUBLE].");
    }
}
