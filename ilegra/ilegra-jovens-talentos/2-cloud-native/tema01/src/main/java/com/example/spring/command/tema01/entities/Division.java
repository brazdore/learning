package com.example.spring.command.tema01.entities;

import com.example.spring.command.tema01.exceptions.CalculatorDivisionException;
import com.example.spring.command.tema01.interfaces.Operation;

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
