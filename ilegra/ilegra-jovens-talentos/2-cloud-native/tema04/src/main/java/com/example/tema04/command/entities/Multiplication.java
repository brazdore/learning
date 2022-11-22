package com.example.tema04.command.entities;

import com.example.tema04.command.interfaces.Operation;

public class Multiplication implements Operation {

    private Double x;
    private Double y;

    public Multiplication() {
    }

    @Override
    public void setValues(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double execute() {
        return x * y;
    }

    @Override
    public String toString() {
        return "Multiplication{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
