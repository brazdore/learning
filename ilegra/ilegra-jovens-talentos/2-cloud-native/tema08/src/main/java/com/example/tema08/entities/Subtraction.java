package com.example.tema08.entities;

public class Subtraction implements Operation {

    private Double x;
    private Double y;

    public Subtraction() {
    }

    public Subtraction(double x, double y) {
        this.x = x;
        this.y = y;
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
