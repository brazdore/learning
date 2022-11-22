package com.example.tema04.command.entities;

import com.example.tema04.command.interfaces.Operation;

public class Exponentiation implements Operation {

    private Double x;
    private Double y;

    public Exponentiation() {
    }

    @Override
    public void setValues(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double execute() {
        return Math.pow(x, y);
    }

    @Override
    public String toString() {
        return "Exponentiation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

