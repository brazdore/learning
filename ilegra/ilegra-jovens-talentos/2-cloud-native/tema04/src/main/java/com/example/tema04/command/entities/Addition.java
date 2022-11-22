package com.example.tema04.command.entities;

import com.example.tema04.command.interfaces.Operation;

public class Addition implements Operation {

    private Double x;
    private Double y;

    public Addition() {
    }

    @Override
    public void setValues(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double execute() {
        return x + y;
    }

    @Override
    public String toString() {
        return "Addition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
