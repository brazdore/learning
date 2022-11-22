package com.example.tema08.entities;

public class Exponentiation implements Operation {

    private Double x;
    private Double y;

    public Exponentiation() {
    }

    public Exponentiation(double x, double y) {
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

