package com.example.tema04.command.entities;

import com.example.tema04.command.exceptions.CalculatorDivisionException;
import com.example.tema04.command.interfaces.Operation;

import java.util.Objects;

public class Division implements Operation {

    private Double x;
    private Double y;

    public Division() {
    }

    @Override
    public void setValues(Double x, Double y) {
        if (Objects.equals(Math.abs(y), 0D)) {
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
