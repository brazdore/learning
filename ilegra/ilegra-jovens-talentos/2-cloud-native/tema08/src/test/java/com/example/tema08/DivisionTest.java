package com.example.tema08;

import com.example.tema08.entities.Division;
import com.example.tema08.exceptions.CalculatorDivisionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DivisionTest {

    private Division division;

    @Test
    public void shouldCreateDivisionObject() {
        Assertions.assertDoesNotThrow(() -> division = new Division(2D, 2D), "Should create [DIVISION] object.");
    }

    @Test
    public void shouldThrowAtDivisionWithZeroConstructor() {
        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                division = new Division(2D, 0D), "Should throw when creating [DIVISION] by zero.");

        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                division = new Division(2D, -0D), "Should throw when creating [DIVISION] by zero.");
    }

    @Test
    public void shouldThrowAtDivisionWithZeroSetters() {
        division = new Division();

        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                division.setValues(2D, 0D), "Should throw when setting [DIVISION] by zero.");

        Assertions.assertThrows(CalculatorDivisionException.class, () ->
                division.setValues(2D, -0D), "Should throw when setting [DIVISION] by zero.");
    }

}
