package com.example.tema06.entities;

import com.example.tema06.interfaces.Operation;

public class Subtraction implements Operation {

    private Double x;
    private Double y;

    public Subtraction() {
    }

    @Override
    public void setValues(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double execute() {
        return x - y;
    }

    @Override
    public String toString() {
        return "Subtraction{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
