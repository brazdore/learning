package com.example.tema08.entities;

import com.example.tema08.exceptions.CalculatorDivisionException;

public class Division implements Operation {

    private Double x;
    private Double y;

    public Division() {
    }

    public Division(double x, double y) {
        if (y == 0D) {
            throw new CalculatorDivisionException("It is impossible to divide by [ZERO]");
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public void setValues(Double x, Double y) {
        if (y == 0D) {
            throw new CalculatorDivisionException("It is impossible to divide by [ZERO]");
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public Double execute() {
        return x / y;
    }

    @Override
    public String toString() {
        return "Division{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
